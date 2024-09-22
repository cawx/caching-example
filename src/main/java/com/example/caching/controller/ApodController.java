package com.example.caching.controller;

import com.example.caching.service.ApodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ApodController {

    @Autowired
    private ApodService apodService;

    @GetMapping("/apod")
    public Mono<String> getApodData() {
        return apodService.getApod();
    }
}
