package com.springboot.government_data_project.service;

// 네이버 검색 API 예제 - 블로그 검색
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.government_data_project.domain.News;
import com.springboot.government_data_project.dto.news.NaverResponseDTO;
import com.springboot.government_data_project.dto.news.NewsListDTO;
import com.springboot.government_data_project.dto.news.RowDTO;
import com.springboot.government_data_project.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NaverSearchService {
    String clientId = "Ep0D4n1_bLF30ZC4gACJ"; //애플리케이션 클라이언트 아이디
    String clientSecret = "gmeLrdmzqG"; //애플리케이션 클라이언트 시크릿

    @Autowired
    private NewsRepository newsRepository;
    public void search(String query) throws JsonProcessingException {
        String text;
        try {
            text = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text; // JSON 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseJson = get(apiURL, requestHeaders);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> apiJson = (Map<String, Object>) objectMapper.readValue(responseJson, Map.class);
        List<Map<String, Object>> itemsList = (List<Map<String, Object>>) apiJson.get("items");

        NewsListDTO newsListDTO = new NewsListDTO();

        for (Map<String, Object> item : itemsList) {
            String compMainTitle = (String) item.get("title");
            String regDate = (String) item.get("pubDate");
            String compContent = (String) item.get("description");
            String linkUrl = (String) item.get("link");

            RowDTO rowDTO = new RowDTO(compMainTitle, regDate, compContent, linkUrl);
            newsListDTO.addNews(rowDTO);
        }

        saveNews(newsListDTO); //db저장

        return;
    }

    protected boolean isNewsExists(String newsUrl){
        return newsRepository.existsNewsByUrl(newsUrl);
    }

    public void saveNews(NewsListDTO newsListDTO){
        newsListDTO.getNewsList().stream().forEach( row ->{
            if(isNewsExists(row.getLinkUrl()) == false){
                News news = new News(row.getCompMainTitle(),row.getRegDate(), row.getCompContent(), row.getLinkUrl());
                newsRepository.save(news);
            }
        });

    }

    public void run() {


        String text = null;
        try {
            text = URLEncoder.encode("그린팩토리", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // JSON 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);


        System.out.println(responseBody);
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
