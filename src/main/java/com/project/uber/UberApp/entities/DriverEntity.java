package com.project.uber.UberApp.entities;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "drivers")
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private Boolean available;

    private String vehicleId;
    @Column(columnDefinition = "Geometry(Point,4326)")
    Point currentLocation;

}
