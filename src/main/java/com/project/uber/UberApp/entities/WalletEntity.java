package com.project.uber.UberApp.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet")
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance=0.0;

    @OneToOne(fetch = FetchType.LAZY , optional = false)
    private UserEntity user;

    @OneToMany(mappedBy = "wallet" , fetch = FetchType.LAZY)
    private List<WalletTransactionEntity> transactions;
}
