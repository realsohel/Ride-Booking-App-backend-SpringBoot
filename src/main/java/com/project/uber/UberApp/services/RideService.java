package com.project.uber.UberApp.services;

import com.project.uber.UberApp.dto.RideDto;
import com.project.uber.UberApp.dto.RideRequestDto;
import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.RideRequestEntity;
import com.project.uber.UberApp.entities.RiderEntity;
import com.project.uber.UberApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    RideEntity getRideById(Long rideId);

    RideEntity createNewRide(RideRequestEntity rideRequestDto, DriverEntity driverEntity);
    RideEntity updateRideStatus(RideEntity rideEntity, RideStatus rideStatus);

    Page<RideEntity> getAllRidesOfRider(RiderEntity rider, PageRequest pageRequest);
    Page<RideEntity> getAllRidesOfDriver(DriverEntity driver, PageRequest pageRequest);

}
