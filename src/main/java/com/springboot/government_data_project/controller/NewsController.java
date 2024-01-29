package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.dto.NewsResponseDTO;
import com.springboot.government_data_project.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class NewsController {
    private final NewsService newsService;
    private final WebClient webClient;

    public NewsController(NewsService newsService, WebClient.Builder webClientBuilder) {
        this.newsService = newsService;
        this.webClient = webClientBuilder.baseUrl("https://open.assembly.go.kr/portal").build();
    }

    @GetMapping("/news")
    public NewsResponseDTO getNews(@RequestParam String date) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/openapi/nzdppcljavkxnylqs")
                        .queryParam("Type", "json")
                        .queryParam("REG_DATE", date)
                        .build())
                .header("Content-Type", "application/json")
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("API request failed with error response: " + errorBody))))
                .bodyToMono(NewsResponseDTO.class)
                .block();
    }
}