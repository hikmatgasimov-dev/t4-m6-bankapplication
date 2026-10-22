package com.example.bank_application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @PostMapping("/account")
    public String createAccount()
    {
        return "";
    }

    @GetMapping("/account/{id}")
    public String getAccount(@PathVariable long id) {

        return "";
    }

    @GetMapping("/account/{id}/transactions")
    public String getTransaction(@PathVariable long id) {

        return "";
    }




}
