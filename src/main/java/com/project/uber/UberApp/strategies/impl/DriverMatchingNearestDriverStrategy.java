package com.project.uber.UberApp.strategies.impl;

import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {
    @Override
    public List<DriverEntity> findMatchingDriver(RideRequestDto rideRequestDto) {
        return null;
    }
}
