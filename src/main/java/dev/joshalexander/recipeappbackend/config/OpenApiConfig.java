package dev.joshalexander.recipeappbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI recipeAppOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Recipe App API")
                        .description("Interactive API documentation for the Recipe Management Application")
                        .version("1.0.0"));
    }
}