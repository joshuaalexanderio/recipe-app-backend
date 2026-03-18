package dev.joshalexander.recipeappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RecipeAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeAppBackendApplication.class, args);
    }

}