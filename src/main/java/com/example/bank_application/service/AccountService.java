package com.example.bank_application.service;

import com.example.bank_application.dto.CreateAccountRequest;
import com.example.bank_application.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import com.example.bank_application.model.Transaction;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AccountService {

    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);
    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    public Account createAccount(CreateAccountRequest request) {

        if (request == null ||
                request.getOwnerName() == null ||
                request.getOwnerName().isBlank() ||
                request.getIban() == null ||
                request.getIban().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Owner name and IBAN are required"
            );
        }

        BigDecimal balance = request.getInitialBalance();

        if (balance == null) {
            balance = BigDecimal.ZERO;
        }

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Initial balance cannot be negative"
            );
        }

        Account account = Account.builder()
                .id(nextId.getAndIncrement())
                .iban(request.getIban())
                .ownerName(request.getOwnerName())
                .balance(balance)
                .build();

        accounts.put(account.getId(), account);

        return account;
    }

    public Account getAccountById(Long id) {

        Account account = id == null ? null : accounts.get(id);

        if (account == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found"
            );
        }

        return account;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> findTransactionsByAccountId(Long accountId) {
        return transactions.stream()
                .filter(t -> t.getAccount() != null && accountId.equals(t.getAccount().getId()))
                .toList();
    }
}
