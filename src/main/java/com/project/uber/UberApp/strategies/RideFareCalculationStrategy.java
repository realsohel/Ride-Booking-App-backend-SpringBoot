package com.project.uber.UberApp.strategies;

import com.project.uber.UberApp.dto.RideRequestDto;

public interface RideFareCalculationStrategy {
    double calculateFare(RideRequestDto rideRequestDto);
}
