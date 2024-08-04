package com.project.uber.UberApp.entities;


import com.project.uber.UberApp.entities.enums.PaymentMethod;
import com.project.uber.UberApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RideRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickUpLocation;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropOffLocation;

    private Double fare;
    @CreationTimestamp
    private LocalDateTime requestedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private RiderEntity rider;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private RideRequestStatus rideRequestStatus;

}
