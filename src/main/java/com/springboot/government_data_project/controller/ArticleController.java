package com.springboot.government_data_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ArticleController
{

    @GetMapping("/article")
    public String getArticle(){
        System.out.println("article 요청");
        return "date";
    }
}
