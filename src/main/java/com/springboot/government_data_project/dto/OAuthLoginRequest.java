package com.springboot.government_data_project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OAuthLoginRequest {
    private String accessToken;
    private String type;

    public OAuthLoginRequest(String accessToken) { this.accessToken = accessToken;}

}
