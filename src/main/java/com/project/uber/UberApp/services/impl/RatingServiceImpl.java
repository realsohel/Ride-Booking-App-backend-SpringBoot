package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.RiderDto;
import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.RatingEntity;
import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.RiderEntity;
import com.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.project.uber.UberApp.exceptions.RuntimeConflictException;
import com.project.uber.UberApp.repositories.DriverRepository;
import com.project.uber.UberApp.repositories.RatingRepository;
import com.project.uber.UberApp.repositories.RiderRepository;
import com.project.uber.UberApp.services.RatingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;

    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public DriverDto RateDriver(RideEntity ride,Integer rating) {
        DriverEntity driver = ride.getDriver();
        RatingEntity ratingObj = ratingRepository.findByRide(ride).orElseThrow(
                ()->new ResourceNotFoundException("Rating not found with rideId: "+ ride.getId())
        );

        if(ratingObj.getDriverRating()!=null){
            throw new RuntimeConflictException("Driver has already been rated...");
        }
        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(RatingEntity::getDriverRating)
                .average()
                .orElse(0.0);

        driver.setRating(newRating);

        DriverEntity savedDriver = driverRepository.save(driver);

        return modelMapper.map(savedDriver,DriverDto.class);
    }

    @Override
    @Transactional
    public RiderDto RateRider(RideEntity ride, Integer rating) {
        RiderEntity rider = ride.getRider();

        RatingEntity ratingObj = ratingRepository.findByRide(ride).orElseThrow(
                ()->new ResourceNotFoundException("Ride not found with rideId: "+ ride.getId())
        );
        if(ratingObj.getRiderRating()!=null){
            throw new RuntimeConflictException("Driver has already been rated...");
        }

        ratingObj.setRiderRating(rating);

        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(RatingEntity::getRiderRating)
                .average()
                .orElse(0.0);

        rider.setRating(newRating);

        RiderEntity savedRider = riderRepository.save(rider);

        return modelMapper.map(savedRider, RiderDto.class);
    }

    @Override
    public void createRating(RideEntity ride) {
        RatingEntity rating =  RatingEntity
                .builder()
                .ride(ride)
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .build();
        ratingRepository.save(rating);
    }
}
