package com.example.SpringBoot.Client.dto;

import com.example.SpringBoot.Client.entity.Clients;

public class ClientsMapper {
    public static Clients toEntity(ClientsRequestDTO dto) {
        return Clients.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .number(dto.getNumber())
                .build();
    }

    public static ClientsResponseDTO toDTO(Clients entity) {
        ClientsResponseDTO dto = new ClientsResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setNumber(entity.getNumber());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
