package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.enums.RideStatus;
import com.project.uber.UberApp.services.RideService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RideServiceImpl implements RideService {
    @Override
    public RideEntity getRideById(Long rideId) {
        return null;
    }

    @Override
    public void matchWithDrivers(RideRequestDto rideRequestDto) {

    }

    @Override
    public RideEntity createNewRide(RideRequestDto rideRequestDto) {
        return null;
    }

    @Override
    public RideEntity updateRideStatus(Long rideId, RideStatus rideStatus) {
        return null;
    }

    @Override
    public Page<RideEntity> getAllRidesOfRider(Long riderId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<RideEntity> getAllRidesOfDriver(Long driverId, PageRequest pageRequest) {
        return null;
    }
}
