package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.RideDto;
import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.dto.RiderDto;
import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.RideRequestEntity;
import com.project.uber.UberApp.entities.RiderEntity;
import com.project.uber.UberApp.entities.UserEntity;
import com.project.uber.UberApp.entities.enums.RideRequestStatus;
import com.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.project.uber.UberApp.repositories.RideRequestRepository;
import com.project.uber.UberApp.repositories.RiderRepository;
import com.project.uber.UberApp.services.RiderService;
import com.project.uber.UberApp.strategies.RideStrategyManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {
    private final ModelMapper modelMapper;

    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

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
    public RideRequestDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return null;
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
//        TODO: Implement Spring Security
        return riderRepository.findById(1L).orElseThrow(()->
                new ResourceNotFoundException("Rider not found with ID: 1" )
        );
    }
}
