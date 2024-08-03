package com.project.uber.UberApp.dto;

import com.project.uber.UberApp.entities.RiderEntity;
import com.project.uber.UberApp.entities.enums.PaymentMethod;
import com.project.uber.UberApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {
    private Long id;

    private Point pickUpLocation;
    private Point dropOffLocation;
    private LocalDateTime requestedTime;

    private RideDto rider;

    private PaymentMethod paymentMethod;
    private RideRequestStatus rideRequestStatus;
}
