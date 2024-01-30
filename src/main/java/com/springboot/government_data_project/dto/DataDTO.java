package com.springboot.government_data_project.dto;

import java.util.List;
import lombok.Data;

@Data
public class DataDTO {
    private List<HeadDTO> head;
    private List<RowDTO> row;
}
