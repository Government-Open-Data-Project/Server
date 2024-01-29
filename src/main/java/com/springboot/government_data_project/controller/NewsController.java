package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.dto.NewsResponseDTO;
import com.springboot.government_data_project.service.NewsService;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@RestController
@RequestMapping("/api")
public class NewsController
{
    private final NewsService newsService;

    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }
    @GetMapping("/news")
    public NewsResponseDTO getArticle(@RequestParam("date") String date){
        //date 형식이 날짜 형식인지 확인

        NewsResponseDTO newsResponseDTO = newsService.getNews(date);
        System.out.println(newsResponseDTO);

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsResponseDTO;
    }
}
