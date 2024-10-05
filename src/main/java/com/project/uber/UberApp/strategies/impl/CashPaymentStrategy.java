package com.project.uber.UberApp.strategies.impl;

import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.PaymentEntity;
import com.project.uber.UberApp.entities.WalletEntity;
import com.project.uber.UberApp.entities.enums.PaymentStatus;
import com.project.uber.UberApp.entities.enums.TransactionMethod;
import com.project.uber.UberApp.repositories.PaymentRepository;
import com.project.uber.UberApp.services.PaymentService;
import com.project.uber.UberApp.services.WalletService;
import com.project.uber.UberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Fare = 100
// Rider gives -> 100 cash to driver
// Driver gets -> 100 cash , but 30rs is deducted from its wallet for commition.
@RequiredArgsConstructor
@Service
public class CashPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(PaymentEntity payment) {

        DriverEntity driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission,
                null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
