package com.springboot.government_data_project.service;

import com.springboot.government_data_project.dto.LoadDataFromNaverResultDTO;
import com.springboot.government_data_project.dto.OAuthLoginRequest;
import com.springboot.government_data_project.util.OauthResourceApi;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public LoadDataFromNaverResultDTO loadData(OAuthLoginRequest oAuthLoginRequest){
        String type = oAuthLoginRequest.getType();
        String accessToken = oAuthLoginRequest.getAccessToken();
        LoadDataFromNaverResultDTO loadDataFromNaverResultDTO =null;

        switch (type){
            case "naver" :
                loadDataFromNaverResultDTO = OauthResourceApi.loadDataFromNaver(accessToken);
                break;
            default:
                throw new RuntimeException("잘못된 플랫폼 입니다.");
        }
        return loadDataFromNaverResultDTO;
    }
}
