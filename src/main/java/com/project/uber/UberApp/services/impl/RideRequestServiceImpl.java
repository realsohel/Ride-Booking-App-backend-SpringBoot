package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.entities.RideRequestEntity;
import com.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.project.uber.UberApp.repositories.RideRequestRepository;
import com.project.uber.UberApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RideRequestServiceImpl implements RideRequestService {
    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequestEntity findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(()-> new ResourceNotFoundException("RideRequest not found with id : "+ rideRequestId));
    }

    @Override
    public void update(RideRequestEntity rideRequestEntity) {
        RideRequestEntity toSave = rideRequestRepository.findById(rideRequestEntity.getId())
                .orElseThrow(()->new ResourceNotFoundException("Ride Request not found with id : "+ rideRequestEntity.getId()));
        rideRequestRepository.save(toSave);
    }
}
