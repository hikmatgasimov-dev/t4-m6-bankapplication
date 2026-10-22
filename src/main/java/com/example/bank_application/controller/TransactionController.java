package com.example.bank_application.controller;

import com.example.bank_application.dto.TransactionResponse;
import com.example.bank_application.dto.TransferRequest;
import com.example.bank_application.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TransactionController {

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransferRequest request) {


        //todo: this should be method of service
        Transaction transaction = new Transaction();
        BigDecimal amount = transaction.getAmount() == null
                ? null
                : BigDecimal.valueOf(transaction.getAmount());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        TransactionResponse
                                .builder()
                                .id(transaction.getId())
                                .accountId(transaction.getAccount().getId())
                                .type(transaction.getType())
                                .amount(amount)
                                .createdAt(transaction.getCreatedAt())
                                .note(transaction.getNote())
                                .build()
                );
    }
}
