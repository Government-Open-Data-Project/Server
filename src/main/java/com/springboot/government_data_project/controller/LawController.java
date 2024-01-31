package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.dto.law.LawResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/law")
public class LawController {

    @GetMapping
    public ResponseEntity<List<LawResponse>> getLawData(){
        List<LawResponse> laws = new ArrayList<>();
        laws.add(new LawResponse("환경정책기본법", "환경정책기본법은 환경보전협회를 한국환경보전원으로 변경하여 공공기관으로서의 공공성과 책임성을 강화합니다. 하수도법 개정은 공공하수도에 대한 기술진단의 공정성을 강화하고, 환경교육의 활성화 및 지원에 관한 법률은 초등·중학교에서 학교환경교육 실시를 의무화합니다"));
        return ResponseEntity.status(HttpStatus.OK).body(laws);
    }
}
