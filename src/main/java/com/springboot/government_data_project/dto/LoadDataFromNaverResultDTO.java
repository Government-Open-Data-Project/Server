package com.springboot.government_data_project.dto;

import com.springboot.government_data_project.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoadDataFromNaverResultDTO {
    private String userId;
    private String name;
    private String gender;
    private String birthyear;
    private String mobile;

    public Member createMember(){
        return new Member(userId, name, gender, birthyear, mobile);
    }
}
