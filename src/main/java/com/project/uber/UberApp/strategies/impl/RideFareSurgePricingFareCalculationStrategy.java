package com.project.uber.UberApp.strategies.impl;

import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.entities.RideRequestEntity;
import com.project.uber.UberApp.services.DistanceService;
import com.project.uber.UberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {
    private final DistanceService distanceService;
    private static final double SURGE_FACTOR = 2.0;
    @Override
    public double calculateFare(RideRequestEntity rideRequestEntity) {
        double distance = distanceService.calculateDistance(rideRequestEntity.getPickUpLocation(), rideRequestEntity.getDropOffLocation());
        return distance * RIDE_FARE_MULTIPLIER * SURGE_FACTOR;
    }
}
