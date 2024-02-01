package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.dto.KakaoTokenDTO;
import com.springboot.government_data_project.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login/oauth")
@RequiredArgsConstructor
public class LoginController {

    private final KakaoLoginService kakaoLoginService;
    @GetMapping("/kakao")
    public void KakaoLogin(@RequestParam String code){
        //redirect uri인 서버로 받은 코드 값으로 엑세스 토큰 요청
        System.out.println("Code :  " + code);
        KakaoTokenDTO kakaoTokenDTO =  kakaoLoginService.getAccessToken(code);
        kakaoLoginService.save
    }
}
