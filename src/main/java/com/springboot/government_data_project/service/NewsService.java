package com.springboot.government_data_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.springboot.government_data_project.domain.News;
import com.springboot.government_data_project.dto.news.NewsListDTO;
import com.springboot.government_data_project.dto.news.NewsResponseDTO;
import com.springboot.government_data_project.dto.news.RowDTO;
import com.springboot.government_data_project.repository.NewsRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
@Slf4j
public class NewsService {
    private final WebClient webClient;

    @Autowired
    private NewsRepository newsRepository;

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

    protected boolean isNewsExists(String newsUrl){
        return newsRepository.existsNewsByUrl(newsUrl);
    }

    public void saveNews(RowDTO rowDTO){
        if(isNewsExists(rowDTO.getLinkUrl()) == false){
            News news = new News(rowDTO.getCompMainTitle(),rowDTO.getRegDate(), rowDTO.getCompContent(), rowDTO.getLinkUrl());
            newsRepository.save(news);
        }
    }

    public void getNewsByAgeRange(String date, String age){
        List<News> newsList;
        switch (age){
            case "20":

        }
    }

    /**
     * 토론회/세미나 뉴스
     * @param date
     * @return
     * @throws JsonProcessingException
     */
    public NewsResponseDTO getDebateNews(String date) throws JsonProcessingException {
//        LocalDate regDate = LocalDate.of(year, month, day);
//        String formattedRegDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

         String jsonString = webClient.get().uri(
                        uriBuilder -> uriBuilder
                                .path("/nzdppcljavkxnylqs")
                                .queryParam("Type", responseType)
                                .queryParam("REG_DATE" ,date)
                                .queryParam("pIndex", 1)
                                .queryParam("pSize", 5)
                                .build())
                .accept(MediaType.APPLICATION_JSON) // Accept 헤더 설정
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        NewsResponseDTO newsResponseDTO = objectMapper.readValue(jsonString, NewsResponseDTO.class);

        System.out.println("뉴스 가져오기 성공 ");

        return newsResponseDTO;

    }


    // 연령대별 조회수를 기준으로 뉴스를 가져오는 메서드
    public List<News> getTopNewsByAgeGroupViews(String ageGroup) {
        switch (ageGroup.toLowerCase()) {
            case "twenties":
                return newsRepository.findAllByOrderByTwentiesViewsDesc();
            case "thirties":
                return newsRepository.findAllByOrderByThirtiesViewsDesc();
            case "forties":
                return newsRepository.findAllByOrderByFortiesViewsDesc();
            case "fifties":
                return newsRepository.findAllByOrderByFiftiesViewsDesc();
            case "sixties":
                return newsRepository.findAllByOrderBySixtiesViewsDesc();
            default:
                throw new IllegalArgumentException("Invalid age group: " + ageGroup);
        }
    }
}
