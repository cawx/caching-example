package com.example.caching.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApodService {

    @Value("${nasa_api_key}")
    private String apiKey;  // The API key is injected here

    private final WebClient webClient;

    public ApodService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.nasa.gov").build();  // Set base URL here
    }

    @Cacheable(value = "apodCache")
    public Mono<String> getApod() {
        // Construct the full URL with the API key inside the method
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/planetary/apod")
                        .queryParam("api_key", apiKey)  // Add the API key dynamically
                        .build())
                .retrieve()
                .bodyToMono(String.class);  // Return the Mono directly
    }
}
