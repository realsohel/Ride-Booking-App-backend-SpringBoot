package com.project.uber.UberApp.repositories;

import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.UserEntity;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity,Long> {

    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickUpLocation) AS distance " +
            "FROM drivers  d " +
            "where d.available = true AND ST_DWithin(d.current_location, :pickUpLocation, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10" , nativeQuery = true
    )
    List<DriverEntity> findTenNearestDrivers(Point pickUpLocation);



    @Query(value = "SELECT d.* " +
            "FROM drivers  d " +
            "where d.available=true  AND ST_DWithin(d.current_location, :pickUpLocation, 10000) " +
            "ORDER BY d.rating DESC " +
            "LIMIT 10" , nativeQuery = true
    )
    List<DriverEntity> findTenNearbyTopRatedDrivers(Point pickUpLocation);

    Optional<DriverEntity> findByUser(UserEntity user);
}
