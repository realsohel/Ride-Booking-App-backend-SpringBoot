package com.project.uber.UberApp.strategies.impl;

import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {
    @Override
    public double calculateFare(RideRequestDto rideRequestDto) {
        return 0;
    }
}
