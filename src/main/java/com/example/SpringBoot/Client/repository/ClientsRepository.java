package com.example.SpringBoot.Client.repository;

import com.example.SpringBoot.Client.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Clients, Long> {
    boolean existsByEmail(String email);
}
