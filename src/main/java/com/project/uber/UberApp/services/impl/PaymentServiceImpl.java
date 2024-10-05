package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.entities.PaymentEntity;
import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.enums.PaymentStatus;
import com.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.project.uber.UberApp.repositories.PaymentRepository;
import com.project.uber.UberApp.services.PaymentService;
import com.project.uber.UberApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentStrategyManager paymentStrategyManager;
    @Override
    public void processPayment(RideEntity ride) {
        PaymentEntity payment = paymentRepository.findByRide(ride).orElseThrow(
                ()->new ResourceNotFoundException("Payment not found for ride: "+ ride.getId())
        );
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public PaymentEntity createNewPayment(RideEntity ride) {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentMethod(ride.getPaymentMethod());
        payment.setAmount(ride.getFare());
        payment.setRide(ride);
        payment.setPaymentStatus(PaymentStatus.PENDING);

        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(PaymentEntity payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }
}
