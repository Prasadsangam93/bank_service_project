package com.bank.service;

import com.bank.dto.*;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import com.bank.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public CustomerService(CustomerRepository repository,
                           AuthenticationManager authManager,
                           PasswordEncoder encoder,
                           JwtUtil jwtUtil) {
        this.repository = repository;
        this.authManager = authManager;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public Customer register(RegisterRequest request) {
        Customer customer = new Customer(
                null,
                request.getFullName(),
                request.getEmail(),
                encoder.encode(request.getPassword()),
                request.getRole()
        );
        return repository.save(customer);
    }

    public AuthResponse login(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token, request.getEmail());
    }

    public List<Customer> getAll() {
        return repository.findAll();
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
    public Customer getLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }

}
