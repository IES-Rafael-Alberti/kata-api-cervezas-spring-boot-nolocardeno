package com.kata.cervezas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Cervezas")
                        .version("1.0.0")
                        .description("API REST para la gestión de cervezas, cerveceras, categorías y estilos. " +
                                "Esta API permite realizar operaciones CRUD sobre cervezas y consultar información " +
                                "de cerveceras, categorías y estilos de cerveza.")
                        .contact(new Contact()
                                .name("Kata Spring Boot")
                                .email("contacto@kata.com")
                                .url("https://github.com/IES-Rafael-Alberti"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo local")
                ));
    }
}
