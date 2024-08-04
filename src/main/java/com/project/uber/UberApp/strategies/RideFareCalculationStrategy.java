package com.project.uber.UberApp.strategies;

import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.entities.RideRequestEntity;

public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10;
    double calculateFare(RideRequestEntity rideRequestEntity);
}
