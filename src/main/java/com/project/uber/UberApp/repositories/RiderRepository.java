package com.project.uber.UberApp.repositories;

import com.project.uber.UberApp.entities.RiderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends JpaRepository<RiderEntity,Long> {
}
