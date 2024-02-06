package com.springboot.government_data_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.government_data_project.domain.LawVote;
import com.springboot.government_data_project.domain.VoteType;
import com.springboot.government_data_project.domain.Law;
import com.springboot.government_data_project.dto.law.WrapperResponseDTO;
import com.springboot.government_data_project.repository.LawRepository;
import com.springboot.government_data_project.repository.LawVoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Slf4j
@Service
public class LawService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    @Autowired
    private LawRepository lawRepository;

    @Autowired
    private LawVoteRepository lawVoteRepository;

    @Value("${government.key}")
    private String governmentApiKey; // application.properties에서 API 키를 주입받습니다.

    public LawService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newHttpClient();
    }

    @Transactional
    public void vote(Long billId, String userId, VoteType voteType) {
        // 해당 법안 찾기
        Law law = lawRepository.findByBillId(billId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid billId: " + billId));

        // 이전 투표 기록 확인
        Optional<LawVote> existingVote = lawVoteRepository.findByBillIdAndUserId(billId, userId);

        if (existingVote.isPresent()) {
            LawVote currentVote = existingVote.get();

            // 이미 같은 방식으로 투표한 경우, 추가 작업 없이 종료
            if (currentVote.getVoteType().equals(voteType)) {
                return; // 이미 투표한 경우, 투표를 무효화하지 않고 무시합니다.
            } else {
                // 반대 투표를 취소하고 새 투표 반영
                switch (currentVote.getVoteType()) {
                    case LIKE:
                        law.setLikes(law.getLikes() - 1); // 이전에 좋아요 했던 것을 취소
                        if (voteType == VoteType.DISLIKE) {
                            law.setDislikes(law.getDislikes() + 1); // 새로운 싫어요 반영
                        }
                        break;
                    case DISLIKE:
                        law.setDislikes(law.getDislikes() - 1); // 이전에 싫어요 했던 것을 취소
                        if (voteType == VoteType.LIKE) {
                            law.setLikes(law.getLikes() + 1); // 새로운 좋아요 반영
                        }
                        break;
                }

                // 기존 투표 기록을 새로운 투표로 업데이트
                currentVote.setVoteType(voteType);
                lawVoteRepository.save(currentVote);
            }
        } else {
            // 새로운 투표 처리
            LawVote newVote = new LawVote();
            newVote.setBillId(billId);
            newVote.setUserId(userId);
            newVote.setVoteType(voteType);

            lawVoteRepository.save(newVote);

            // LawResponseDTO의 likes 또는 dislikes 업데이트
            if (voteType == VoteType.LIKE) {
                law.setLikes(law.getLikes() + 1);
            } else if (voteType == VoteType.DISLIKE) {
                law.setDislikes(law.getDislikes() + 1);
            }
        }

        lawRepository.save(law);
    }


    public WrapperResponseDTO getLaw() {
        String url = "https://open.assembly.go.kr/portal/openapi/TVBPMBILL11";

        // 정확한 요청 파라미터 구성
        String queryParams = String.format(
                "?KEY=%s&Type=json&pIndex=%d&pSize=%d",
                governmentApiKey, // API 키 변수 사용
                1, // pIndex 값
                10 // pSize 값 수정
        );

        String urlWithParams = url + queryParams;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlWithParams))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
           // 응답 바디를 WrapperResponseDTO 객체로 변환하여 반환
            return objectMapper.readValue(response.body(), WrapperResponseDTO.class);
        } catch (IOException | InterruptedException e) {
            System.err.println("HTTP request failed: " + e.getMessage());
            Thread.currentThread().interrupt(); // InterruptedException 발생 시 스레드 중단 처리
            return null;
        }
    }

    public WrapperResponseDTO searchLaws(String keyword) {
        String url = "https://open.assembly.go.kr/portal/openapi/TVBPMBILL11";

        // 정확한 요청 파라미터 구성
        String queryParams = String.format(
                "?KEY=%s&Type=json&pIndex=%d&pSize=%d&BILL_NAME=%s",
                governmentApiKey, // API 키 변수 사용
                1, // pIndex 값
                10, // pSize 값 수정
                keyword
        );

        String urlWithParams = url + queryParams;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlWithParams))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // 응답 바디를 WrapperResponseDTO 객체로 변환하여 반환
            return objectMapper.readValue(response.body(), WrapperResponseDTO.class);
        } catch (IOException | InterruptedException e) {
            System.err.println("HTTP request failed: " + e.getMessage());
            Thread.currentThread().interrupt(); // InterruptedException 발생 시 스레드 중단 처리
            return null;
        }
    }
}
