package com.pulsenotify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI pulseNotifyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PulseNotify API")
                        .description("Centralized notification platform providing unified notification APIs.")
                        .version("1.0.0"));
    }
}
