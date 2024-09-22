package com.example.caching.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApodService {

    @Value("${nasa_api_key}")
    private String apiKey;

    private final WebClient webClient;

    public ApodService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.nasa.gov").build();
    }

    @Cacheable(value = "apodCache")
    public Mono<String> getApod() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/planetary/apod")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
