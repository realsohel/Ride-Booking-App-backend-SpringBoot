package com.project.uber.UberApp.repositories;

import com.project.uber.UberApp.entities.PaymentEntity;
import com.project.uber.UberApp.entities.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByRide(RideEntity ride);
}
