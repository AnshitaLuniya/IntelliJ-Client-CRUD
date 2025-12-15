package com.example.SpringBoot.Client.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI clientAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Client Management API")
                        .description("CRUD API for Client Details")
                        .version("1.0"));
    }
}