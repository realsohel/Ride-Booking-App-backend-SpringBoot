package com.project.uber.UberApp.services;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.RiderDto;
import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.RiderEntity;

public interface RatingService {

    RiderDto RateRider(RideEntity ride, Integer rating);
    DriverDto RateDriver(RideEntity ride, Integer rating);

    void createRating(RideEntity ride);
}
