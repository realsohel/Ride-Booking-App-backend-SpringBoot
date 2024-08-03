package com.project.uber.UberApp.repositories;

import com.project.uber.UberApp.entities.RideRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequestEntity,Long> {

}
