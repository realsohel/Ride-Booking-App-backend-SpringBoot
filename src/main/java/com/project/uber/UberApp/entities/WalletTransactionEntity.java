package com.project.uber.UberApp.entities;


import com.project.uber.UberApp.entities.enums.TransactionMethod;
import com.project.uber.UberApp.entities.enums.TransactionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class WalletTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private String transactionId;

    @OneToOne
    private RiderEntity ride;

    @ManyToOne
    private WalletEntity wallet;

    @CreationTimestamp
    private LocalDateTime timeStamp;

}
