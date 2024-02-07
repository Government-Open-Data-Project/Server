package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.domain.Law;
import com.springboot.government_data_project.domain.LawVote;
import com.springboot.government_data_project.domain.VoteType;
import com.springboot.government_data_project.dto.law.*;
import com.springboot.government_data_project.dto.userProfile.RecommendProfileDTO;
import com.springboot.government_data_project.repository.LawRepository;
import com.springboot.government_data_project.repository.LawVoteRepository;
import com.springboot.government_data_project.service.LawService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@RestController
@RequestMapping("/api/law")
public class LawController {

    private final LawService lawService;
    private final LawRepository lawRepository;
    private final LawVoteRepository lawVoteRepository;

    private static final Logger logger = LoggerFactory.getLogger(LawController.class);


    public LawController(LawService lawService, LawRepository lawRepository, LawVoteRepository lawVoteRepository) {
        this.lawService = lawService;
        this.lawRepository = lawRepository;
        this.lawVoteRepository = lawVoteRepository;
    }
    @Operation(summary = "테스트 기능")
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        WrapperResponseDTO wrapperResponseDTO = lawService.getLaw();
        List<Law> lawList = new ArrayList<>();

        logger.info("wrapper length = " + wrapperResponseDTO.getTvbpmbill11().size());
        logger.info("tv length = " + wrapperResponseDTO.getTvbpmbill11().get(1).getRow().size());
        List<Law> laws = wrapperResponseDTO.getTvbpmbill11().get(1).getRow();
        laws.forEach(law -> {
            // 크롤링 함수 crawlLawContent을 호출하여 법률 내용을 가져옵니다.
            String lawContent = crawlLawContent(law.getLinkUrl());

            // LawResponseDTO 객체를 빌더 패턴을 사용하여 생성합니다.
            Law dto = Law.builder()
                    .title(law.getTitle())
                    .content(lawContent)
                    .linkUrl(law.getLinkUrl())
                    .build();

            // 생성된 dto 객체를 lawList에 추가합니다.
            lawList.add(dto);

            // 로깅
            logger.info("Bill Title: {}, Link URL: {}", law.getTitle(), law.getLinkUrl());
        });


        return ResponseEntity.ok(lawList);
    }

    @Operation(summary = "법안 검색하는 기능")
    @GetMapping("/search")
    public ResponseEntity<List<Law>> searchLaws(SearchCriteria criteria) {
        WrapperResponseDTO wrapperResponseDTO = lawService.searchLaws(criteria.getKeyword());
        List<Law> lawList = new ArrayList<>();
        if(wrapperResponseDTO.getTvbpmbill11() == null){
            return ResponseEntity.ok().body(lawList);
        }
        List<Law> laws = wrapperResponseDTO.getTvbpmbill11().get(1).getRow();
        laws.forEach(law -> {
            // 크롤링 함수 crawlLawContent을 호출하여 법률 내용을 가져옵니다.
            String lawContent = crawlLawContent(law.getLinkUrl());



            // LawResponseDTO 객체를 빌더 패턴을 사용하여 생성합니다.
            Law dto = Law.builder()
                    .title(law.getTitle())
                    .content(lawContent)
                    .linkUrl(law.getLinkUrl())
                    .build();

            // 생성된 dto 객체를 lawList에 추가합니다.
            lawList.add(dto);

        });

        return ResponseEntity.status(HttpStatus.OK).body(lawList);
    }

    @Operation(summary = "법안 정책 가져오는 기능")
    @GetMapping
    public ResponseEntity<List<Law>> getLawData(){
        WrapperResponseDTO wrapperResponseDTO = lawService.getLaw();
        List<Law> lawList = new ArrayList<>();
        List<Law> laws = wrapperResponseDTO.getTvbpmbill11().get(1).getRow();
        laws.forEach(law -> {
            // 데이터베이스에서 해당 bill_id를 가진 엔티티의 존재 여부를 확인합니다.
            Optional<Law> existingLaw = lawRepository.findById(law.getBillId());
            if (!existingLaw.isPresent()) { // 해당 엔티티가 존재하지 않는 경우
                // 크롤링 함수 crawlLawContent을 호출하여 법률 내용을 가져옵니다.
                String lawContent = crawlLawContent(law.getLinkUrl());

                // LawResponseDTO 객체를 빌더 패턴을 사용하여 생성합니다.
                Law dto = Law.builder()
                        .billId(law.getBillId())
                        .title(law.getTitle())
                        .content(lawContent)
                        .linkUrl(law.getLinkUrl())
                        .build();

                // 새로운 데이터를 데이터베이스에 저장합니다.
                lawRepository.save(dto);

                // 생성된 dto 객체를 lawList에 추가합니다.
                lawList.add(dto);
            } else {
                lawList.add(existingLaw.get());
                // 데이터가 이미 존재하는 경우, 필요한 로직을 수행합니다. (예: 로그 출력)
                System.out.println("Data for bill_id " + law.getBillId() + " already exists and will not be updated.");
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(lawList);
    }

    @Operation(summary = "법안 추천")
    @PostMapping ("/recommended")
    public ResponseEntity<List<Law>> getReconmmendedLawData(@RequestBody RecommendProfileDTO userProfileUpdateDTO){
        WrapperResponseDTO wrapperResponseDTO = lawService.getLaw();
        List<Law> lawList = new ArrayList<>();
        List<Law> laws = wrapperResponseDTO.getTvbpmbill11().get(1).getRow();
        laws.forEach(law -> {
            // 크롤링 함수 crawlLawContent을 호출하여 법률 내용을 가져옵니다.
            String lawContent = crawlLawContent(law.getLinkUrl());

            // LawResponseDTO 객체를 빌더 패턴을 사용하여 생성합니다.
            Law dto = Law.builder()
                    .title(law.getTitle())
                    .content(lawContent)
                    .linkUrl(law.getLinkUrl())
                    .build();

            // 생성된 dto 객체를 lawList에 추가합니다.
            lawList.add(dto);

        });

        return ResponseEntity.status(HttpStatus.CREATED).body(lawList);
    }
 
    @GetMapping("/community")
    public ResponseEntity<List<CommunityLawResponseDTO>> getCommunityLawData(Principal principal) {
        String userId = principal.getName(); // 현재 로그인한 사용자의 ID
        WrapperResponseDTO wrapperResponseDTO = lawService.getLaw();
        List<CommunityLawResponseDTO> responseList = new ArrayList<>();
        List<Law> laws = wrapperResponseDTO.getTvbpmbill11().get(1).getRow();

        laws.forEach(law -> {
            Optional<Law> existingLaw = lawRepository.findById(law.getBillId());
            Optional<LawVote> lawVote = lawVoteRepository.findByBillIdAndUserId(law.getBillId(), userId);

            String isChecked = lawVote.map(vote -> {
                if (vote.getVoteType() == VoteType.LIKE) {
                    return "LIKE";
                } else if (vote.getVoteType() == VoteType.DISLIKE) {
                    return "DISLIKE";
                }
                return "NONE";
            }).orElse("NONE");

            if (!existingLaw.isPresent()) {
                String lawContent = crawlLawContent(law.getLinkUrl());
                Law newLaw = Law.builder()
                        .billId(law.getBillId())
                        .title(law.getTitle())
                        .content(lawContent)
                        .linkUrl(law.getLinkUrl())
                        .build();
                lawRepository.save(newLaw);
                responseList.add(convertToDto(newLaw, isChecked));
            } else {
                responseList.add(convertToDto(existingLaw.get(), isChecked));
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    private CommunityLawResponseDTO convertToDto(Law law, String isChecked) {
        return CommunityLawResponseDTO.builder()
                .billId(law.getBillId())
                .title(law.getTitle())
                .content(law.getContent())
                .linkUrl(law.getLinkUrl())
                .likes(law.getLikes())
                .dislikes(law.getDislikes())
                .isChecked(isChecked)
                .build();
    }

    @Operation(summary = "법안에 대해 좋아요/싫어요 투표 기능")
    @PostMapping("/{billId}/vote")
    public ResponseEntity<?> vote(@PathVariable Long billId, @RequestBody VoteRequest voteRequest, Principal principal) {
        logger.info("principal name : " + principal.getName());
        // UserDetails에서 사용자 ID 추출 (인증 구현 필요)
        String userId = principal.getName();
        VoteType voteType = VoteType.valueOf(voteRequest.getVoteType().toUpperCase());

        lawService.vote(billId, userId, voteType);

        return ResponseEntity.ok().build();
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
                //System.out.println("Crawled Data: " + cleanText);
                return cleanText;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during crawling: " + e.getMessage();
        }
        return "Content not found";
    }


}

@Data
class VoteRequest {
    private String voteType; // "LIKE" or "DISLIKE"

    // Getter와 Setter 생략
}
