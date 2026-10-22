package com.example.bank_application.controller;

import com.example.bank_application.dto.AccountResponse;
import com.example.bank_application.dto.CreateAccountRequest;
import com.example.bank_application.dto.TransactionResponse;
import com.example.bank_application.model.Account;
//import com.example.bank_application.service.AccountService;
import com.example.bank_application.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        return toAccountResponse(getAccountOrThrow(id));
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionResponse> getTransactions(@PathVariable Long id) {
        getAccountOrThrow(id);

        return accountService.findTransactionsByAccountId(id).stream()
                .map(this::toTransactionResponse)
                .toList();
    }

    private Account getAccountOrThrow(Long id) {
        return accountService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found: " + id));
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
