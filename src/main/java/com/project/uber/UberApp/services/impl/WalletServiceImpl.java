package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.entities.*;
import com.project.uber.UberApp.entities.enums.TransactionMethod;
import com.project.uber.UberApp.entities.enums.TransactionType;
import com.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.project.uber.UberApp.repositories.WalletRepository;
import com.project.uber.UberApp.services.WalletService;
import com.project.uber.UberApp.services.WalletTransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    @Override
    @Transactional
    public WalletEntity addMoneyToWallet(UserEntity user, Double amount, String transactionId, RideEntity ride, TransactionMethod transactionMethod) {
        WalletEntity wallet = findWalletByUser(user);
        wallet.setBalance(wallet.getBalance()+amount);

        WalletTransactionEntity walletTransaction = new WalletTransactionEntity()
                .builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public WalletEntity deductMoneyFromWallet(UserEntity user, Double amount, String transactionId, RideEntity ride, TransactionMethod transactionMethod) {
        WalletEntity wallet = findWalletByUser(user);
        wallet.setBalance(wallet.getBalance()-amount);

        WalletTransactionEntity walletTransaction = new WalletTransactionEntity()
                .builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

//        walletTransactionService.createNewWalletTransaction(walletTransaction);

        wallet.getTransactions().add(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public WalletEntity findWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(
                ()->new ResourceNotFoundException("Wallet not found with Id: "+ walletId)
        );
    }

    @Override
    public WalletEntity createNewWallet(UserEntity user) {
        WalletEntity wallet = new WalletEntity();
        wallet.setUser(user);

        return walletRepository.save(wallet);


    }

    @Override
    public WalletEntity findWalletByUser(UserEntity user) {
        return walletRepository.findByUser( user)
                .orElseThrow(()->new ResourceNotFoundException("Wallet not found for user with id: "+ user.getId()));

    }
}
