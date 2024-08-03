package com.project.uber.UberApp.services;

import com.project.uber.UberApp.dto.RideDto;
import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    RideEntity getRideById(Long rideId);

    void matchWithDrivers(RideRequestDto rideRequestDto);
    RideEntity createNewRide(RideRequestDto rideRequestDto);
    RideEntity updateRideStatus(Long rideId, RideStatus rideStatus);

    Page<RideEntity> getAllRidesOfRider(Long riderId, PageRequest pageRequest);
    Page<RideEntity> getAllRidesOfDriver(Long driverId, PageRequest pageRequest);

}
