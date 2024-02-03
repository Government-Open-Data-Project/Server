package com.springboot.government_data_project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
public class KakaoApi {

    @Value("kakao.api_key")
    private String kakaoApiKey;

    @Value("kakao.redirect_uri")
    private String kakaoRedirectUri;

    @Value("kakao.admin_key")
    private String kakaoAdminKey;

}
