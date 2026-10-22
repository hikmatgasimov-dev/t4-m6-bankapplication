package com.example.bank_application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Long id;
    private Account account;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private String note;
}
