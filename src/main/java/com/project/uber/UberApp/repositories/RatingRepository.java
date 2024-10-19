package com.project.uber.UberApp.repositories;

import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.RatingEntity;
import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.RiderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    List<RatingEntity> findByRider(RiderEntity rider);
    List<RatingEntity> findByDriver(DriverEntity driver);

    Optional<RatingEntity> findByRide(RideEntity ride);
}
