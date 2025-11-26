package com.bank.controller;

import com.bank.dto.AccountResponse;
import com.bank.entity.Account;
import com.bank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return ResponseEntity.ok(service.create(account));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.getAccountsByCustomer(customerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAccountWithCustomer(id));
    }
}
