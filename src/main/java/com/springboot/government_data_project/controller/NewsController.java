package com.springboot.government_data_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.government_data_project.dto.news.NewsListDTO;
import com.springboot.government_data_project.dto.news.NewsResponseDTO;
import com.springboot.government_data_project.service.NewsService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NewsController
{
    private final NewsService newsService;
    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }

    public static boolean isValidDate(String date) {
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    @GetMapping("/news")
    public NewsListDTO getArticle(@RequestParam("date") String date) throws JsonProcessingException {
        NewsListDTO newsListDTO = new NewsListDTO();
        //date 형식이 날짜 형식인지 확인
        if(isValidDate(date)){
            NewsResponseDTO newsResponseDTO = newsService.getNews(date); //해당하는 날짜의 뉴스를 가져옴
            if(newsResponseDTO.getResult() == null) { //date에 해당하는 뉴스 기사 존재
                newsListDTO.addNews(newsResponseDTO.getDataList().get(1).getRow()); //모든 기사 넣기
            }

        }
        return newsListDTO;

    }
}
