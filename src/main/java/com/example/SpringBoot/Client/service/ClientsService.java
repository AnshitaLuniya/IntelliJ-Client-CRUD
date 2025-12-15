package com.example.SpringBoot.Client.service;

import com.example.SpringBoot.Client.dto.ClientsRequestDTO;
import com.example.SpringBoot.Client.dto.ClientsResponseDTO;

public interface ClientsService {
    ClientsResponseDTO createClient(ClientsRequestDTO dto);
    ClientsResponseDTO getClient(Long id);
    ClientsResponseDTO updateClient(Long id, ClientsRequestDTO dto);
    void deleteClient(Long id);
}
