package com.project.uber.UberApp.repositories;

import com.project.uber.UberApp.entities.UserEntity;
import com.project.uber.UberApp.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
    Optional<WalletEntity> findByUser(UserEntity user);
}
