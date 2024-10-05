package com.project.uber.UberApp.strategies;

import com.project.uber.UberApp.entities.PaymentEntity;

public interface PaymentStrategy {

    Double PLATFORM_COMMISSION = 0.1;

    void processPayment(PaymentEntity payment);
}
