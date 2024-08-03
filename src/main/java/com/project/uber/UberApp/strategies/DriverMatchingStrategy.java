package com.project.uber.UberApp.strategies;

import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.entities.DriverEntity;

import java.util.List;

public interface DriverMatchingStrategy {
    List<DriverEntity> findMatchingDriver(RideRequestDto rideRequestDto);
}
