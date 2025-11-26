package com.bank.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {


    private Long id;
    private String accountNumber;
    private String accountType;
    private double balance;
    private CustomerResponse customer;
}
