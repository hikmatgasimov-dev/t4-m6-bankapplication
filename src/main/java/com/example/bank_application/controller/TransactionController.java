package com.example.bank_application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @PostMapping("/transfer")
    public String createTransaction() {
        return "";
    }
}
