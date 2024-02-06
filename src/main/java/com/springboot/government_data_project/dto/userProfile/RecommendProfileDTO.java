package com.springboot.government_data_project.dto.userProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendProfileDTO {
    private List<String> age; // 나이
    private List<String> isMarried; // 결혼여부
    private List<String> region; // 거주지역
    private List<String> position; // 직위
    private List<String> interests; // 관심분야
}
