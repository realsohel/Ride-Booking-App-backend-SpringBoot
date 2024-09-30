package com.project.uber.UberApp.strategies.impl;

import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.RideRequestEntity;
import com.project.uber.UberApp.repositories.DriverRepository;
import com.project.uber.UberApp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;
    @Override
    public List<DriverEntity> findMatchingDriver(RideRequestEntity rideRequestEntity) {
        return driverRepository.findTenNearbyTopRatedDrivers(rideRequestEntity.getPickUpLocation());
    }





}
