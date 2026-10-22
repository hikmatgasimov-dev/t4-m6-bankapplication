package com.example.bank_application.controller;

import com.example.bank_application.dto.TransactionResponse;
import com.example.bank_application.dto.TransferRequest;
import com.example.bank_application.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransferRequest request) {


        //todo: this should be method of service
        Transaction something = new Transaction();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        TransactionResponse
                                .builder()
                                .id(something.getId())
                                .accountId(something.getAccount().getId())
                                .type(something.getType())
                                .amount(something.getAmount())
                                .createdAt(something.getCreatedAt())
                                .note(something.getNote())
                                .build()
                );
    }
}
