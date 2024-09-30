package com.project.uber.UberApp.services;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.RideDto;
import com.project.uber.UberApp.dto.RiderDto;
import com.project.uber.UberApp.entities.DriverEntity;

import java.util.List;

public interface DriverService {
    RideDto acceptRide(Long rideRequestId);
    RideDto cancelRide(Long rideId);
    RideDto startRide(Long rideId, String otp);
    RideDto endtRide(Long rideId);
    RiderDto rateRider(Long rideId,Integer rating);

    DriverDto getMyProfile();

    List<RideDto> getAllMyRides();

    DriverEntity getCurrentDriver();
}
