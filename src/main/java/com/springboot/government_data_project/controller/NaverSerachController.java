package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.service.NaverSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class NaverSerachController {

    /*@Autowired
    private NaverSearchService naverSearchService;

    @GetMapping("/naver")
    public ResponseEntity<String> searchNaver(@RequestParam String query) {
        try {
            String result = naverSearchService.search(query);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            // 오류 처리 로직 (예: 로깅)
            return ResponseEntity.internalServerError().body("검색 중 오류 발생");
        }
    }*/
}
