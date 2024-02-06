package com.springboot.government_data_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.government_data_project.domain.News;
import com.springboot.government_data_project.dto.news.NaverResponseDTO;
import com.springboot.government_data_project.dto.news.NewsListDTO;
import com.springboot.government_data_project.dto.news.NewsResponseDTO;
import com.springboot.government_data_project.service.NaverSearchService;
import com.springboot.government_data_project.service.NewsService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NewsController
{
    private final NewsService newsService;

    @Autowired
    private NaverSearchService naverSearchService;
    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }

    public static boolean isValidDate(String date) {
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    public static boolean isValidAge(int age){
        if(age % 10 ==0){
            return true;
        }
        return false;
    }

    @GetMapping("/news")
    public NewsListDTO getArticle(@RequestParam("date") String date) throws JsonProcessingException {
        NewsListDTO newsListDTO = new NewsListDTO();
        //date 형식이 날짜 형식인지 확인
        if(isValidDate(date)){

            /**
             * 토론회 뉴스
             */
            NewsResponseDTO newsResponseDTO = newsService.getDebateNews(date); //토론회 뉴스
            if(newsResponseDTO.getResult() == null) { //date에 해당하는 뉴스 기사 존재
                newsListDTO.addAllNews(newsResponseDTO.getDataList().get(1).getRow()); //모든 기사 넣기

                newsListDTO.getNewsList().stream().forEach(newsRow ->{ //뉴스 디비에 저장
                    newsService.saveNews(newsRow);
                });

                newsListDTO.clearNews();
            }

            /**
             * 정당 뉴스
             */
            newsResponseDTO = newsService.getPoliticalParty(date); //토론회 뉴스
            if(newsResponseDTO.getResult() == null) { //date에 해당하는 뉴스 기사 존재
                newsListDTO.addAllNews(newsResponseDTO.getDataList().get(1).getRow()); //모든 기사 넣기

                newsListDTO.getNewsList().stream().forEach(newsRow ->{ //뉴스 디비에 저장
                    newsService.saveNews(newsRow);
                });

                newsListDTO.clearNews();
            }

            /**
             * 위원회 뉴스
             */
            newsResponseDTO = newsService.getCommittee(date); //토론회 뉴스
            if(newsResponseDTO.getResult() == null) { //date에 해당하는 뉴스 기사 존재
                newsListDTO.addAllNews(newsResponseDTO.getDataList().get(1).getRow()); //모든 기사 넣기

                newsListDTO.getNewsList().stream().forEach(newsRow ->{ //뉴스 디비에 저장
                    newsService.saveNews(newsRow);
                });

                newsListDTO.clearNews();
            }

            /**
             * 의장단
             */
            newsResponseDTO = newsService.getChairman(); //토론회 뉴스
            if(newsResponseDTO.getResult() == null) { //date에 해당하는 뉴스 기사 존재

                newsListDTO.addAllNews(newsResponseDTO.getDataList().get(1).getRow()); //모든 기사 넣기

                newsListDTO.getNewsList().stream().forEach(newsRow ->{ //뉴스 디비에 저장
                    newsService.saveNews(newsRow);
                });

                newsListDTO.clearNews();
            }
            newsListDTO = newsService.getCurrentNews();

        }
        return newsListDTO;

    }



    @GetMapping("/news/region")
    public NewsListDTO geTestNews(@RequestParam("region") String region) throws JsonProcessingException {

        naverSearchService.search(region + " 정당");
        naverSearchService.search(region + " 국회");
        naverSearchService.search(region + " 법안");
        naverSearchService.search(region + " 법률");
        naverSearchService.search(region + " 의회");
        naverSearchService.search(region + " 의원");

        NewsListDTO newsListDTO = newsService.getRegionNews(region);

        return newsListDTO;

    }


    // 연령대별 조회수가 높은 뉴스를 가져오는 엔드포인트
    @GetMapping("/top-by-age-group/{age}")
    public ResponseEntity<NewsListDTO> getTopNewsByAgeGroup(@PathVariable int age) {
        String ageGroup = determineAgeGroup(age);
        NewsListDTO newsListDTO = new NewsListDTO();

        try {
            List<News> newsList = newsService.getTopNewsByAgeGroupViews(ageGroup);

            newsList.forEach(element ->
                    newsListDTO.getNewsList().add(element.toRowData())
            );

            return ResponseEntity.ok(newsListDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 잘못된 연령대가 입력된 경우
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // 그 외 에러 처리
        }
    }

    // 나이를 바탕으로 연령대 그룹을 결정하는 메서드
    private String determineAgeGroup(int age) {
        if (age >= 20 && age <= 29) {
            return "twenties";
        } else if (age >= 30 && age <= 39) {
            return "thirties";
        } else if (age >= 40 && age <= 49) {
            return "forties";
        } else if (age >= 50 && age <= 59) {
            return "fifties";
        } else if (age >= 60 && age <= 69) {
            return "sixties";
        } else if( age >= 70){
            return "seventies";
        }
        else {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
    }

}
