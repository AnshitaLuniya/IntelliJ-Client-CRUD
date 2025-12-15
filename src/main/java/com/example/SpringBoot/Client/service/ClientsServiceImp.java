package com.example.SpringBoot.Client.service;

import com.example.SpringBoot.Client.dto.ClientsMapper;
import com.example.SpringBoot.Client.dto.ClientsRequestDTO;
import com.example.SpringBoot.Client.dto.ClientsResponseDTO;
import com.example.SpringBoot.Client.entity.Clients;
import com.example.SpringBoot.Client.exception.DuplicateResourceException;
import com.example.SpringBoot.Client.exception.ResourceNotFoundException;
import com.example.SpringBoot.Client.repository.ClientsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientsServiceImp implements ClientsService{
    @Autowired
    private ClientsRepository repo;

    @Override
    public ClientsResponseDTO createClient(ClientsRequestDTO dto) {

        log.info("Creating client {}", dto.getEmail());

        if (repo.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        Clients client = ClientsMapper.toEntity(dto);
        Clients saved = repo.save(client);

        return ClientsMapper.toDTO(saved);
    }

    @Override
    public ClientsResponseDTO getClient(Long id) {

        log.info("Fetching client {}", id);

        Clients client = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        return ClientsMapper.toDTO(client);
    }

    @Override
    public ClientsResponseDTO updateClient(Long id, ClientsRequestDTO dto) {

        Clients client = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        log.info("Updating client {}", id);

        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setNumber(dto.getNumber());

        return ClientsMapper.toDTO(repo.save(client));
    }

    @Override
    public void deleteClient(Long id) {

        log.warn("Deleting client {}", id);

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Client not found");
        }

        repo.deleteById(id);
    }
}
