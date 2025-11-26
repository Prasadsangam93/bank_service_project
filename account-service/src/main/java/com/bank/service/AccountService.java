package com.bank.service;


import com.bank.config.CustomerClient;
import com.bank.dto.AccountResponse;
import com.bank.dto.CustomerResponse;
import com.bank.entity.Account;
import com.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repo;
    private final CustomerClient customerClient;

    public AccountService(AccountRepository repo, CustomerClient customerClient) {
        this.repo = repo;
        this.customerClient = customerClient;
    }

    public Account create(Account account) {
        return repo.save(account);
    }

    public List<Account> getAccountsByCustomer(Long customerId) {
        System.out.println("Looking for accounts of customerId = " + customerId);
        return repo.findByCustomerId(customerId);
    }

    public AccountResponse getAccountWithCustomer(Long id) {

        Account acc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Feign call → Customer-Service
        CustomerResponse customer = customerClient.getCustomer(acc.getCustomerId());

        return new AccountResponse(
                acc.getId(),
                acc.getAccountNumber(),
                acc.getAccountType(),
                acc.getBalance(),
                customer

        );

    }
}
