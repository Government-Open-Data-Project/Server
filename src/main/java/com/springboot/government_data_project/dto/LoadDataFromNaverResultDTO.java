package com.springboot.government_data_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoadDataFromNaverResultDTO {
    private String id;
    private String name;
    private String gender;
    private String birthyear;
    private String mobile;
}
