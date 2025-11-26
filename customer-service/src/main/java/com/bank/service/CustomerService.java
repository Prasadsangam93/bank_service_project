package com.bank.service;

import com.bank.dto.*;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import com.bank.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repo;
    private final AuthenticationManager auth;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public CustomerService(CustomerRepository repo,
                           AuthenticationManager auth,
                           PasswordEncoder encoder,
                           JwtUtil jwt) {
        this.repo = repo;
        this.auth = auth;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public Customer register(RegisterRequest req) {
        Customer c = new Customer(
                null,
                req.getFullName(),
                req.getEmail(),
                encoder.encode(req.getPassword()),
                req.getRole()
        );
        return repo.save(c);
    }

    public AuthResponse login(AuthRequest req) {

        auth.authenticate(new UsernamePasswordAuthenticationToken(
                req.getEmail(), req.getPassword()));

        String token = jwt.generateToken(req.getEmail());

        return new AuthResponse(token, req.getEmail());
    }

    public Customer getById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new RuntimeException("User Not Found"));
    }

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Customer getByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + email));
    }
}
