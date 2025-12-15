package com.example.SpringBoot.Client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientsRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid is email")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String number;
}
