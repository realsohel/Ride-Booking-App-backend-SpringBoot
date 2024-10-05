package com.project.uber.UberApp.services;

import com.project.uber.UberApp.entities.PaymentEntity;
import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.enums.PaymentStatus;
import com.project.uber.UberApp.strategies.PaymentStrategyManager;

public interface PaymentService {

    void processPayment(RideEntity ride);
    PaymentEntity createNewPayment(RideEntity ride);

    void updatePaymentStatus(PaymentEntity payment, PaymentStatus paymentStatus);
}
