package com.example.bank_application.dto;

import com.example.bank_application.model.TransactionType;
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
@Builder
public class TransactionResponse {

    private Long id;
    private Long accountId;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private String note;
}