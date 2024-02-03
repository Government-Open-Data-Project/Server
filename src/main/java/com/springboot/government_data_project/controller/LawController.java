package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.dto.law.LawListResponseDTO;
import com.springboot.government_data_project.dto.law.LawResponseDTO;
import com.springboot.government_data_project.dto.law.WrapperResponseDTO;
import com.springboot.government_data_project.service.LawService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final LawService lawService;
    private static final Logger logger = LoggerFactory.getLogger(LawController.class);


    public LawController(LawService lawService) {
        this.lawService = lawService;
    }
    @Operation(summary = "테스트 기능")
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        WrapperResponseDTO wrapperResponseDTO = lawService.getLaw();

        logger.info("wrapper length = " + wrapperResponseDTO.getTvbpmbill11().size());
        logger.info("tv length = " + wrapperResponseDTO.getTvbpmbill11().get(1).getRow().size());
        // 첫 번째 "row" 데이터를 사용합니다.
        List<LawResponseDTO> laws = wrapperResponseDTO.getTvbpmbill11().get(1).getRow();
        laws.forEach(law -> {
            logger.info("Bill Title: {}, Link URL: {}", law.getTitle(), law.getLinkUrl());
        });

        return ResponseEntity.ok(laws);
    }

    @Operation(summary = "법안 정책 가져오는 기능")
    @GetMapping
    public ResponseEntity<List<LawResponseDTO>> getLawData(){


        List<LawResponseDTO> laws = new ArrayList<>();
        laws.add(new LawResponseDTO("환경정책기본법", "환경정책기본법은 환경보전협회를 한국환경보전원으로 변경하여 공공기관으로서의 공공성과 책임성을 강화합니다. 하수도법 개정은 공공하수도에 대한 기술진단의 공정성을 강화하고, 환경교육의 활성화 및 지원에 관한 법률은 초등·중학교에서 학교환경교육 실시를 의무화합니다"));
        return ResponseEntity.status(HttpStatus.OK).body(laws);
    }
}
