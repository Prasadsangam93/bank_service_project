package com.bank.config;

import com.bank.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {

    @GetMapping("/customer/{id}")
    CustomerResponse getCustomer(@PathVariable("id") Long id);


}
