package dev.joshalexander.recipeappbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class TodoistRestClientConfig {

    /**
     * A plain RestClient used for both the OAuth token exchange
     * and later for REST API calls to Todoist.
     * No webflux dependency needed — runs on the servlet stack.
     */
    @Bean
    public RestClient todoistRestClient() {
        return RestClient.builder()
                .build();
    }
}