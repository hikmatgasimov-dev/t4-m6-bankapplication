package com.example.bank_application.controller;

import com.example.bank_application.dto.TransactionResponse;
import com.example.bank_application.dto.TransferRequest;
import com.example.bank_application.model.Transaction;
import com.example.bank_application.service.AccountService;
import com.example.bank_application.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transfer")
public class TransactionController {
    private final TransferService transferService;

    public TransactionController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransferRequest request) {


        Transaction transaction = (transferService.transfer(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        TransactionResponse
                                .builder()
                                .id(transaction.getId())
                                .accountId(transaction.getAccount().getId())
                                .type(transaction.getType())
                                .amount(transaction.getAmount())
                                .createdAt(transaction.getCreatedAt())
                                .note(transaction.getNote())
                                .build()
                );
    }
}
