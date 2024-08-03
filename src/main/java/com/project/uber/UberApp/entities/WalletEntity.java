package com.project.uber.UberApp.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToMany(mappedBy = "wallet" , fetch = FetchType.LAZY)
    private List<WalletTransactionEntity> transactions;
}
