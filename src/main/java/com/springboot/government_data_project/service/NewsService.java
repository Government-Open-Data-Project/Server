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

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
@Transactional
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

    /**
     * url의 조회수중 ageGroup에 맞는조회수 증가
     * @param ageGroup
     * @param url
     */
    public void increaseNewsViews(String ageGroup , String url){
        switch (ageGroup) {
            case  "twenties" :
                newsRepository.incrementTwentiesViewByUrl(url);
                break;
            case "thirties" :
                newsRepository.incrementThirtiesViewByUrl(url);
                break;
            case "forties" :
                newsRepository.incrementFortiesViewByUrl(url);
                break;
            case "fifties" :
                newsRepository.incrementFiftiesViewByUrl(url);
                break;
            case "sixties" :
                newsRepository.incrementSixtiesViewByUrl(url);
                break;
            case "seventies" :
                newsRepository.incrementSeventiesViewByUrl(url);
                break;
        }
    }

    public NewsListDTO getCurrentNews(){
        NewsListDTO newsListDTO = new NewsListDTO();

        List<News> newsList = newsRepository.findTop25ByOrderByRegDateDesc();

        newsList.forEach(element ->
                newsListDTO.getNewsList().add(element.toRowData())
        );

        return newsListDTO;
    }

    public NewsListDTO getRegionNews(String region){
        NewsListDTO newsListDTO = new NewsListDTO();

        List<News> newsList = newsRepository.findTop25ByCompMainTitleContainingOrCompContentContainingOrderByRegDateDesc(region, region);

        newsList.forEach(element ->
                newsListDTO.getNewsList().add(element.toRowData())
        );
        return newsListDTO;
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


    /**
     * 정당 뉴스
     * @param date
     * @return
     * @throws JsonProcessingException
     */
    public NewsResponseDTO getPoliticalParty(String date) throws JsonProcessingException {
//        LocalDate regDate = LocalDate.of(year, month, day);
//        String formattedRegDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String jsonString = webClient.get().uri(
                        uriBuilder -> uriBuilder
                                .path("/nbzyjjyoamdqqjorw")
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

        System.out.println("정당 뉴스 가져오기 성공 ");

        return newsResponseDTO;

    }

    /**
     * 위원회 뉴스 가져오기
     * @param date
     * @return
     * @throws JsonProcessingException
     */
    public NewsResponseDTO getCommittee(String date) throws JsonProcessingException {
//        LocalDate regDate = LocalDate.of(year, month, day);
//        String formattedRegDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String jsonString = webClient.get().uri(
                        uriBuilder -> uriBuilder
                                .path("/nuizrfvoaepvwrjtz")
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

        System.out.println("위원회 뉴스 가져오기 성공 ");

        return newsResponseDTO;

    }

    /**
     * 의장단 뉴스 가져오기
     * @return
     * @throws JsonProcessingException
     */
    public NewsResponseDTO getChairman() throws JsonProcessingException {
//        LocalDate regDate = LocalDate.of(year, month, day);
//        String formattedRegDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String jsonString = webClient.get().uri(
                        uriBuilder -> uriBuilder
                                .path("/ndkuweviadcqkbjdl")
                                .queryParam("Type", responseType)
                                .queryParam("pIndex", 1)
                                .queryParam("pSize", 20)
                                .build())
                .accept(MediaType.APPLICATION_JSON) // Accept 헤더 설정
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        NewsResponseDTO newsResponseDTO = objectMapper.readValue(jsonString, NewsResponseDTO.class);

        System.out.println("의장단 뉴스 가져오기 성공 ");

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
            case "seventies":
                return newsRepository.findAllByOrderBySeventiesViewsDesc();
            default:
                throw new IllegalArgumentException("Invalid age group: " + ageGroup);
        }
    }
}
