package com.springboot.government_data_project.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.springboot.government_data_project.dto.LoadDataFromNaverResultDTO;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import org.springframework.web.reactive.function.client.WebClient;

public class OauthResourceApi {
    private static String apiURL = "https://openapi.naver.com/v1/nid/me";

    private static String decodeBase64(String encodedValue) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    public static LoadDataFromNaverResultDTO loadDataFromNaver(String accessToken){

        String headerAuthor = "Bearer " + accessToken;
        WebClient webClient = WebClient.create(apiURL);
        String getResponseString = webClient.get()
                .uri(apiURL)
                .header(HttpHeaders.AUTHORIZATION, headerAuthor)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();


        LoadDataFromNaverResultDTO loadDataFromNaverResultDTO = null;

        try{
//            loadDataFromNaverResultDTO = objectMapper.readValue(getResponseString, LoadDataFromNaverResultDTO.class);
            Map<String, Object> apiJson = (Map<String, Object>) objectMapper.readValue(getResponseString, Map.class).get("response");
            String id = (String) apiJson.get("id");
            String name = (String) apiJson.get("name");
            String gender = (String) apiJson.get("gender");
            String birthyear = (String) apiJson.get("birthyear");
            String mobile = (String) apiJson.get("mobile");

            loadDataFromNaverResultDTO = new LoadDataFromNaverResultDTO(id, name,gender,birthyear,mobile);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }


        System.out.println("회원가입 : "+ loadDataFromNaverResultDTO);

        return loadDataFromNaverResultDTO;



    }
}
