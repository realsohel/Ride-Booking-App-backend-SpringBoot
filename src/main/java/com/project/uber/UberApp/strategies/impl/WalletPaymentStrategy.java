package com.project.uber.UberApp.strategies.impl;

import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.PaymentEntity;
import com.project.uber.UberApp.entities.RiderEntity;
import com.project.uber.UberApp.entities.enums.PaymentStatus;
import com.project.uber.UberApp.entities.enums.TransactionMethod;
import com.project.uber.UberApp.repositories.PaymentRepository;
import com.project.uber.UberApp.repositories.WalletRepository;
import com.project.uber.UberApp.services.PaymentService;
import com.project.uber.UberApp.services.WalletService;
import com.project.uber.UberApp.strategies.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

// Ride have 404 in wallet, Driver have 900 in wallet
// Ride cost -> 100, Commission -> 10
// After ride rider's wallet => 404 - 100 = 304
// After ride driver's wallet => 900 + 90 = 990

@RequiredArgsConstructor
@Service
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;
    @Override
    @Transactional
    public void processPayment(PaymentEntity payment) {

        RiderEntity rider = payment.getRide().getRider();
        DriverEntity driver = payment.getRide().getDriver();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(),null,
                payment.getRide(), TransactionMethod.RIDE);

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.addMoneyToWallet(driver.getUser(), payment.getAmount()-platformCommission,
                null,payment.getRide(),TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}
