package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.dto.law.LawListResponseDTO;
import com.springboot.government_data_project.dto.law.LawResponseDTO;
import com.springboot.government_data_project.dto.law.SearchCriteria;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
        List<LawResponseDTO> lawList = new ArrayList<>();

        logger.info("wrapper length = " + wrapperResponseDTO.getTvbpmbill11().size());
        logger.info("tv length = " + wrapperResponseDTO.getTvbpmbill11().get(1).getRow().size());
        List<LawResponseDTO> laws = wrapperResponseDTO.getTvbpmbill11().get(1).getRow();
        laws.forEach(law -> {
            // 크롤링 함수 crawlLawContent을 호출하여 법률 내용을 가져옵니다.
            String lawContent = crawlLawContent(law.getLinkUrl());

            // LawResponseDTO 객체를 빌더 패턴을 사용하여 생성합니다.
            LawResponseDTO dto = LawResponseDTO.builder()
                    .title(law.getTitle())
                    .content(lawContent)
                    .linkUrl(law.getLinkUrl())
                    .build();

            // 생성된 dto 객체를 lawList에 추가합니다.
            lawList.add(dto);

            // 로깅
            logger.info("Bill Title: {}, Link URL: {}", law.getTitle(), law.getLinkUrl());
        });



        crawlLawContent("https://likms.assembly.go.kr/bill/billDetail.do?billId=PRC_T2S4M0L1K1T7R1R0Z2X5E4D0C7B0J7");


        return ResponseEntity.ok(lawList);
    }

    @Operation(summary = "법안 검색하는 기능")
    @GetMapping("/search")
    public ResponseEntity<List<LawResponseDTO>> searchLaws(SearchCriteria criteria) {
        List<LawResponseDTO> laws = lawService.searchLaws(criteria);
        return ResponseEntity.ok(laws);
    }

    @Operation(summary = "법안 정책 가져오는 기능")
    @GetMapping
    public ResponseEntity<List<LawResponseDTO>> getLawData(){
        WrapperResponseDTO wrapperResponseDTO = lawService.getLaw();
        List<LawResponseDTO> lawList = new ArrayList<>();
        List<LawResponseDTO> laws = wrapperResponseDTO.getTvbpmbill11().get(1).getRow();
        laws.forEach(law -> {
            // 크롤링 함수 crawlLawContent을 호출하여 법률 내용을 가져옵니다.
            String lawContent = crawlLawContent(law.getLinkUrl());

            // LawResponseDTO 객체를 빌더 패턴을 사용하여 생성합니다.
            LawResponseDTO dto = LawResponseDTO.builder()
                    .title(law.getTitle())
                    .content(lawContent)
                    .linkUrl(law.getLinkUrl())
                    .build();

            // 생성된 dto 객체를 lawList에 추가합니다.
            lawList.add(dto);

        });

        return ResponseEntity.status(HttpStatus.OK).body(lawList);
    }


    public String crawlLawContent(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Element contentElement = doc.selectFirst("#summaryContentDiv");

            if (contentElement != null) {
                // HTML 내용을 추출
                String htmlContent = contentElement.html();
                // <br> 태그를 시스템의 줄바꿈 문자로 변환
                String textContent = htmlContent.replace("<br>", "\n").replace("<br/>", "\n");
                // HTML에서 순수 텍스트를 추출
                String cleanText = Jsoup.parseBodyFragment(textContent).text();

                // "제안이유 및 주요내용" 다음에 줄바꿈을 두 번 추가
                cleanText = cleanText.replace("제안이유 및 주요내용", "제안이유 및 주요내용\n\n");

                // 크롤링된 데이터 로깅
                System.out.println("Crawled Data: " + cleanText);
                return cleanText;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during crawling: " + e.getMessage();
        }
        return "Content not found";
    }


}
