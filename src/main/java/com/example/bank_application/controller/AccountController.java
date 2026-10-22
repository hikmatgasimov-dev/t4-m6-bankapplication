package com.example.bank_application.controller;

import com.example.bank_application.dto.AccountResponse;
import com.example.bank_application.dto.CreateAccountRequest;
import com.example.bank_application.dto.TransactionResponse;
import com.example.bank_application.model.Account;
//import com.example.bank_application.service.AccountService;
import com.example.bank_application.model.Transaction;
import com.example.bank_application.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request);
        return toAccountResponse(account);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable Long id) {
        return toAccountResponse(accountService.getAccountById(id));
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionResponse> getTransactions(@PathVariable Long id) {
        accountService.getAccountById(id);
        return accountService.findTransactionsByAccountId(id).stream()
                .map(this::toTransactionResponse)
                .toList();
    }

      private AccountResponse toAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .iban(account.getIban())
                .ownerName(account.getOwnerName())
                .balance(account.getBalance())
                .build();
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        BigDecimal amount = transaction.getAmount() == null
                ? null
                : BigDecimal.valueOf(transaction.getAmount());

        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccount().getId())
                .type(transaction.getType())
                .amount(amount)
                .createdAt(transaction.getCreatedAt())
                .note(transaction.getNote())
                .build();
    }





}
