package com.project.uber.UberApp.repositories;

import com.project.uber.UberApp.entities.RiderEntity;
import com.project.uber.UberApp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<RiderEntity,Long> {
    Optional<RiderEntity> findByUser(UserEntity user);
}
