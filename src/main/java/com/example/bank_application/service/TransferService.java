package com.example.bank_application.service;

import com.example.bank_application.dto.TransferRequest;
import com.example.bank_application.model.Account;
import com.example.bank_application.model.Transaction;
import com.example.bank_application.model.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountService accountService;
    private final List<Transaction> transactions = new ArrayList<>();

    public synchronized Transaction transfer(TransferRequest request) {
        BigDecimal amount = request.getAmount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        if (request.getFromAccountId() == null || request.getToAccountId() == null) {
            throw new IllegalArgumentException("Account IDs cannot be null");
        }

        if (request.getFromAccountId().equals(request.getToAccountId())) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        Account fromAccount = accountService.getAccountById(request.getFromAccountId());
        Account toAccount = accountService.getAccountById(request.getToAccountId());

        if(fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        LocalDateTime now = LocalDateTime.now();

        Transaction debit = new Transaction(
                (long) transactions.size() + 1,
                fromAccount,
                TransactionType.TRANSFER_OUT,
                amount,
                now,
                "Transfer to " + toAccount.getIban()
        );

        transactions.add(debit);


        Transaction credit = new Transaction(
                (long) transactions.size() + 1,
                toAccount,
                TransactionType.TRANSFER_IN,
                amount,
                now,
                "Transfer from " + fromAccount.getIban()
        );

        transactions.add(credit);
        return  debit;
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId){
        return transactions.stream()
                .filter(t -> t.getAccount().getId().equals(accountId))
                .toList();
    }
}
