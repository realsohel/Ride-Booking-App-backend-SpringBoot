package com.project.uber.UberApp.services;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.RideDto;
import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.dto.RiderDto;
import com.project.uber.UberApp.entities.RiderEntity;
import com.project.uber.UberApp.entities.UserEntity;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);
    RideRequestDto cancelRide(Long rideId);
    DriverDto rateDriver(Long rideId, Integer rating);
    RiderDto getMyProfile();
    List<RideDto> getAllMyRides();

    RiderEntity createNewRider(UserEntity user);
}
