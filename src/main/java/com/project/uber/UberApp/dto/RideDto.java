package com.project.uber.UberApp.dto;

import com.project.uber.UberApp.entities.enums.PaymentMethod;
import com.project.uber.UberApp.entities.enums.RideStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideDto {

    private Long id;
    private PointDto pickUpLocation;

    private PointDto dropOffLocation;

    private LocalDateTime createdTime;

    private RiderDto rider;

    private DriverDto driver;

    private PaymentMethod paymentMethod;
    private RideStatus rideStatus;

    private Double fare;
    private String otp;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
