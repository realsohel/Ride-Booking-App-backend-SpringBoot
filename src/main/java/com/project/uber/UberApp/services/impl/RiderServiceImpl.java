package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.RideDto;
import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.dto.RiderDto;
import com.project.uber.UberApp.entities.RideRequestEntity;
import com.project.uber.UberApp.entities.RiderEntity;
import com.project.uber.UberApp.entities.UserEntity;
import com.project.uber.UberApp.entities.enums.RideRequestStatus;
import com.project.uber.UberApp.repositories.RideRequestRepository;
import com.project.uber.UberApp.repositories.RiderRepository;
import com.project.uber.UberApp.services.RiderService;
import com.project.uber.UberApp.strategies.DriverMatchingStrategy;
import com.project.uber.UberApp.strategies.RideFareCalculationStrategy;
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
    private final RideFareCalculationStrategy rideFareCalculationStrategy;
    private final DriverMatchingStrategy driverMatchingStrategy;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        RideRequestEntity rideRequestEntity = modelMapper.map(rideRequestDto,RideRequestEntity.class);
        rideRequestEntity.setRideRequestStatus(RideRequestStatus.PENDING);

        double fare = rideFareCalculationStrategy.calculateFare(rideRequestEntity);
        rideRequestEntity.setFare(fare);
        driverMatchingStrategy.findMatchingDriver(rideRequestEntity);

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
}
