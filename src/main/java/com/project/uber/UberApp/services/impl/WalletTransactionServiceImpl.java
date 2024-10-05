package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.dto.WalletTransactionDto;
import com.project.uber.UberApp.entities.WalletEntity;
import com.project.uber.UberApp.entities.WalletTransactionEntity;
import com.project.uber.UberApp.repositories.WalletTransactionRepository;
import com.project.uber.UberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;
    @Override
    public void createNewWalletTransaction(WalletTransactionEntity walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }
}
