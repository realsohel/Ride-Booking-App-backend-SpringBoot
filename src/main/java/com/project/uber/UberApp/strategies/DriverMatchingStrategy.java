package com.project.uber.UberApp.strategies;

import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.RideRequestEntity;

import java.util.List;

public interface DriverMatchingStrategy {
    List<DriverEntity> findMatchingDriver(RideRequestEntity rideRequestEntity);

//    List<DriverEntity> findMatchingDrivers(RideRequestEntity rideRequestEntity);
}
