package com.example.cursosms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Gerenciamento de Cursos - Microsserviço Cursos")
                .description("Microsserviço de cursos em uma aplicação de gerenciamento de cursos.")
                .version("1.0").contact(new Contact().name("Victor Silva")
                        .email("victorhfsilva@protonmail.com")));
    }

}
