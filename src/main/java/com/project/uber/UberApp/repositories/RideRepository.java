package com.project.uber.UberApp.repositories;

import com.project.uber.UberApp.entities.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<RideEntity,Long> {
}
