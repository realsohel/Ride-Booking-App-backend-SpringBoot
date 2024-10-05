package com.project.uber.UberApp.dto;

import com.project.uber.UberApp.entities.UserEntity;
import com.project.uber.UberApp.entities.WalletTransactionEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {
    private Long id;

    private Double balance;

    private UserDto user;

    private List<WalletTransactionDto> transactions;
}
