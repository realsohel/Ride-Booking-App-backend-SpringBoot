package com.project.uber.UberApp.repositories;

import com.project.uber.UberApp.entities.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity,Long> {
}
