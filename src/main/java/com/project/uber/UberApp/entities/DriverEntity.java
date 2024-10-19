package com.project.uber.UberApp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drivers", indexes = {
        @Index(name = "idx_driver_vehicle_id", columnList = "vehicle_id")
})
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
