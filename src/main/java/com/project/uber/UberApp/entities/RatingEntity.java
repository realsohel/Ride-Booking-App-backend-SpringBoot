package com.project.uber.UberApp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_rating_rider", columnList = "rider_id"),
        @Index(name = "idx_rating_driver", columnList = "driver_id")
})
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private RideEntity ride;

    @ManyToOne
    private RiderEntity rider;

    @ManyToOne
    private DriverEntity driver;

//    Rating for driver...
    private Integer driverRating;

    //    Rating for Rider...
    private Integer riderRating;
}
