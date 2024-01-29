package com.springboot.government_data_project.service;

import com.google.common.net.HttpHeaders;
import com.springboot.government_data_project.dto.NewsResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class NewsService {
    private final WebClient webClient;

    private final String responseType = "json";
    public NewsService(WebClient.Builder webClientBuilder){
        this.webClient =  webClientBuilder.baseUrl("https://open.assembly.go.kr/portal/openapi")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer f68e81f751a640169fd3d0b88e183411")
                .build();
    }

    public String getCurrentDate(){
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        // 원하는 형식으로 날짜 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

    public NewsResponseDTO getNews(){
        return webClient.get().uri(
                uriBuilder -> uriBuilder
                        .path("/nzdppcljavkxnylqs")
                        .queryParam("Type" , responseType)
                        .queryParam("REG_DATE" , getCurrentDate())
                        .build())
                .accept(MediaType.APPLICATION_JSON) // Accept 헤더 설정
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve()
                .bodyToMono(NewsResponseDTO.class)
                .block();
    }

        public NewsResponseDTO getNews(String date){
    //        LocalDate regDate = LocalDate.of(year, month, day);
    //        String formattedRegDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


            return webClient.get().uri(
                            uriBuilder -> uriBuilder
                                    .path("/nzdppcljavkxnylqs")
                                    .queryParam("Type", responseType)
                                    .queryParam("REG_DATE" ,date)
                                    .build())
                    .accept(MediaType.APPLICATION_JSON) // Accept 헤더 설정
                    .retrieve()
                    .bodyToMono(NewsResponseDTO.class)
                    .block();


        }
}
