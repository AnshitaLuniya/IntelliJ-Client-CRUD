package com.example.SpringBoot.Client.controller;


import com.example.SpringBoot.Client.entity.User;
import com.example.SpringBoot.Client.payload.AuthResponse;
import com.example.SpringBoot.Client.payload.LoginRequest;
import com.example.SpringBoot.Client.payload.RegisterRequest;
import com.example.SpringBoot.Client.repository.UserRepository;
import com.example.SpringBoot.Client.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider jwtProvider;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        log.info("Register request received for username: {}", req.getUsername());
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(req.getRole() != null ? req.getRole() : "ROLE_USER");
        repo.save(user);
        log.info("User registered successfully: {}", req.getUsername());
        return "Registered";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        log.info("Login attempt for username: {}", req.getUsername());
        User user = repo.findByUsername(req.getUsername())
                .orElseThrow(() -> {
                    log.warn("Login failed. User not found: {}", req.getUsername());
                    return new RuntimeException("Invalid credentials");
                });

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            log.warn("Login failed. Invalid password for username: {}", req.getUsername());
            throw new RuntimeException("Invalid credentials");
        }

        log.info("Login successful for username: {}", req.getUsername());

        return new AuthResponse(
                jwtProvider.generateToken(user.getUsername(), user.getRole())
        );
    }
}

