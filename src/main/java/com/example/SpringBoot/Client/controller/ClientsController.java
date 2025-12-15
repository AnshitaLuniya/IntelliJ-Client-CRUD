package com.example.SpringBoot.Client.controller;

import com.example.SpringBoot.Client.dto.ClientsRequestDTO;
import com.example.SpringBoot.Client.dto.ClientsResponseDTO;
import com.example.SpringBoot.Client.service.ClientsService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/clients")
public class ClientsController {

    @Autowired
    private ClientsService service;

    @PostMapping("/create")
    public ResponseEntity<ClientsResponseDTO> create(
            @Valid @RequestBody ClientsRequestDTO dto) {

        log.info("API Request: Create client with email={}", dto.getEmail());

        ClientsResponseDTO response = service.createClient(dto);

        log.info("Client created successfully with id={}", response.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ClientsResponseDTO> getById(@PathVariable Long id) {

        log.info("API Request: Get client by id={}", id);

        ClientsResponseDTO response = service.getClient(id);

        log.info("Client fetched successfully for id={}", id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientsResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ClientsRequestDTO dto) {

        log.info("API Request: Update client id={} with new email={}", id, dto.getEmail());

        ClientsResponseDTO response = service.updateClient(id, dto);

        log.info("Client updated successfully for id={}", id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        log.warn("API Request: Delete client id={}", id);

        service.deleteClient(id);

        log.warn("Client deleted successfully for id={}", id);

        return ResponseEntity.ok("Client deleted successfully");
    }
}
