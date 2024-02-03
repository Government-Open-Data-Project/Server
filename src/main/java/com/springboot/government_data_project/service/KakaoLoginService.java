package com.springboot.government_data_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.government_data_project.KakaoApi;
import com.springboot.government_data_project.dto.KakaoProfileDTO;
import com.springboot.government_data_project.dto.KakaoTokenDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class KakaoLoginService {

    private WebClient webClient;
    private KakaoApi kakaoApi = new KakaoApi();

    public KakaoLoginService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("https://kauth.kakao.com")
//                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer aee8599d17154320bdad05a472958803")
                .build();
    }

    public KakaoTokenDTO getAccessToken(String code){

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", kakaoApi.getKakaoApiKey());
        formData.add("redirect_uri", kakaoApi.getKakaoRedirectUri());
        formData.add("code", code);


        String webClientResponseString =  webClient.post()
                .uri("/oauth/token")
                .header("Content-type","application/x-www-form-urlencoded;charset=utf-8" ) //요청 헤더
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoTokenDTO kakaoTokenDTO = null;
        try {
             kakaoTokenDTO = objectMapper.readValue(webClientResponseString, KakaoTokenDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kakaoTokenDTO;
    }


    public KakaoProfileDTO findProfile(String token){
        String response = webClient.post()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + token)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfileDTO kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response, KakaoProfileDTO.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kakaoProfile;

    }

}
