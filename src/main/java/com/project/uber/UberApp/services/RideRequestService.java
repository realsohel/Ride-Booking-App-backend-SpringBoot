package com.project.uber.UberApp.services;

import com.project.uber.UberApp.entities.RideRequestEntity;

public interface RideRequestService {

    RideRequestEntity findRideRequestById(Long rideRequestId);

    void update(RideRequestEntity rideRequestEntity);
}
