package com.springboot.government_data_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.government_data_project.dto.law.WrapperResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
public class LawService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    @Value("${government.key}")
    private String governmentApiKey;

    public LawService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newHttpClient();
    }

    public WrapperResponseDTO getLaw() {
        String url = "https://open.assembly.go.kr/portal/openapi/TVBPMBILL11";

        // 정확한 요청 파라미터 구성
        String queryParams = String.format(
                "KEY=%s&Type=json&pIndex=%d&pSize=%d",
                governmentApiKey, // API 키를 직접 사용
                1, // pIndex 값
                3 // pSize 값
        );

        String urlWithParams = url + "?" + queryParams;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlWithParams))
                .header("Content-Type", "application/json")
                .GET() 
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            // 응답 바디를 WrapperResponseDTO 객체로 변환하여 반환
            return objectMapper.readValue(response.body(), WrapperResponseDTO.class);
        } catch (IOException | InterruptedException e) {
            log.error("HTTP request failed", e);
            Thread.currentThread().interrupt(); // InterruptedException 발생 시 스레드 중단 처리
            return null;
        }
}



}
