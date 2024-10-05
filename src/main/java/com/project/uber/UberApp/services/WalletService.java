package com.project.uber.UberApp.services;

import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.UserEntity;
import com.project.uber.UberApp.entities.WalletEntity;
import com.project.uber.UberApp.entities.enums.TransactionMethod;

public interface WalletService {
    
    WalletEntity addMoneyToWallet(UserEntity user, Double amount, String transactionId, RideEntity ride, TransactionMethod transactionMethod);

    WalletEntity deductMoneyFromWallet(UserEntity user, Double amount, String transactionId, RideEntity ride, TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet(); // This can be done only by the drivers

    WalletEntity findWalletById(Long walletId);

    WalletEntity createNewWallet(UserEntity user);

    WalletEntity findWalletByUser(UserEntity user);
}
