package com.example.SpringBoot.Client.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClientsResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String number;
    private LocalDateTime createdAt;
}
