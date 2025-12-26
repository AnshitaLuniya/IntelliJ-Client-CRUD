package com.example.SpringBoot.Client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import com.example.SpringBoot.Client.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        log.debug("Attempting to load user by username: {}", username);

        var user = repo.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found with username: {}", username);
                       return new UsernameNotFoundException("User not found");
                });
        log.info("User details loaded successfully for username: {}", username);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", ""))
                .build();
    }
}