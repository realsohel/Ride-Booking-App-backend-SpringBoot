package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.RideDto;
import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.dto.RiderDto;
import com.project.uber.UberApp.entities.*;
import com.project.uber.UberApp.entities.enums.RideRequestStatus;
import com.project.uber.UberApp.entities.enums.RideStatus;
import com.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.project.uber.UberApp.repositories.DriverRepository;
import com.project.uber.UberApp.repositories.RideRequestRepository;
import com.project.uber.UberApp.repositories.RiderRepository;
import com.project.uber.UberApp.services.DriverService;
import com.project.uber.UberApp.services.RatingService;
import com.project.uber.UberApp.services.RideService;
import com.project.uber.UberApp.services.RiderService;
import com.project.uber.UberApp.strategies.RideStrategyManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        RideRequestEntity rideRequestEntity = modelMapper.map(rideRequestDto,RideRequestEntity.class);
        rideRequestEntity.setRideRequestStatus(RideRequestStatus.PENDING);

        RiderEntity rider = getCurrentRider();
        rideRequestEntity.setRider(rider);


        double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequestEntity);
        log.info("calculate fare : "+ fare);
        rideRequestEntity.setFare(fare);

        List<DriverEntity> drivers = rideStrategyManager
                .driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequestEntity);

        // TODO: Send notification to all the drivers.
        RideRequestEntity savedRideRequest = rideRequestRepository.save(rideRequestEntity);
//        log.info(rideRequestEntity.toString());
        return modelMapper.map(savedRideRequest,RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {

        RiderEntity rider = getCurrentRider();
        RideEntity ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider cannot cancel the ride as he is not authorised");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled as the status of the ride is not CONFIRMED, the status is : "+ ride.getRideStatus() );
        }

        RideEntity updatedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);

        return modelMapper.map(updatedRide,RideDto.class);
    }

    @Override
    @Transactional
    public DriverDto rateDriver(Long rideId, Integer rating) {
        RideEntity ride = rideService.getRideById(rideId);
        RiderEntity rider = getCurrentRider();

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("User is not the Rider of this ride..");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride is not ended, hence you cannot give the rating now. The status is: "+ ride.getRideStatus() );
        }

        return ratingService.RateDriver(ride, rating);
    }

    @Override
    public RiderDto getMyProfile() {
        RiderEntity rider = getCurrentRider();

        return modelMapper.map(rider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        RiderEntity rider = getCurrentRider();

        return  rideService.getAllRidesOfRider(rider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public RiderEntity createNewRider(UserEntity user) {
        RiderEntity rider = RiderEntity
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public RiderEntity getCurrentRider() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return riderRepository.findByUser(user).orElseThrow(()->
                new ResourceNotFoundException("Rider not associated with user with Id: "+ user.getId() )
        );
    }
}
