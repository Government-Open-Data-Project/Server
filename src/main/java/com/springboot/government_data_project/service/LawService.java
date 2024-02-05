package com.springboot.government_data_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.government_data_project.dto.law.LawResponseDTO;
import com.springboot.government_data_project.dto.law.SearchCriteria;
import com.springboot.government_data_project.dto.law.WrapperResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LawService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${government.key}")
    private String governmentApiKey; // application.properties에서 API 키를 주입받습니다.

    public LawService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newHttpClient();
    }

    public WrapperResponseDTO getLaw() {
        String url = "https://open.assembly.go.kr/portal/openapi/TVBPMBILL11";

        // 정확한 요청 파라미터 구성
        String queryParams = String.format(
                "?KEY=%s&Type=json&pIndex=%d&pSize=%d",
                governmentApiKey, // API 키 변수 사용
                1, // pIndex 값
                10 // pSize 값 수정
        );

        String urlWithParams = url + queryParams;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlWithParams))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
           // 응답 바디를 WrapperResponseDTO 객체로 변환하여 반환
            return objectMapper.readValue(response.body(), WrapperResponseDTO.class);
        } catch (IOException | InterruptedException e) {
            System.err.println("HTTP request failed: " + e.getMessage());
            Thread.currentThread().interrupt(); // InterruptedException 발생 시 스레드 중단 처리
            return null;
        }
    }

    public List<LawResponseDTO> searchLaws(SearchCriteria criteria) {
        // 프론트 구현을 위해 임시로 하드코딩된 데이터를 반환
        List<LawResponseDTO> result = new ArrayList<>();
        result.add(new LawResponseDTO("법안 제목 1", "법안 내용 1", "http://test1"));
        result.add(new LawResponseDTO("법안 제목 2", "법안 내용 2", "http://test2"));

        return result;
    }
}
