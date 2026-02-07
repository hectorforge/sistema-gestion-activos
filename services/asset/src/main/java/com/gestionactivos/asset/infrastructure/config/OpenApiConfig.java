package com.gestionactivos.asset.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI assetOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Asset Service API")
                        .description("API de gestión de activos")
                        .version("v1"));
    }
}