package com.project.uber.UberApp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "rider")
public class RiderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
