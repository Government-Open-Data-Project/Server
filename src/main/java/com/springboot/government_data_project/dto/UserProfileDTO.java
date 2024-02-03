package com.springboot.government_data_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {
    private String name; // 이름
    private int age; // 나이
    private boolean isMarried; // 결혼여부
    private String region; // 거주지역
    private String position; // 직위
    private String interest; // 관심분야
}
