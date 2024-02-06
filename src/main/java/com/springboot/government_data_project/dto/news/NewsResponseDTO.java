package com.springboot.government_data_project.dto.news;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.government_data_project.dto.news.DataDTO;
import com.springboot.government_data_project.dto.news.ResultDTO;
import lombok.Data;

import java.util.List;

@Data
public class NewsResponseDTO {

    @JsonAlias({"nbzyjjyoamdqqjorw", "nzdppcljavkxnylqs" ,"nuizrfvoaepvwrjtz"})
    private List<DataDTO> dataList;

    @JsonProperty("RESULT")
    private ResultDTO result;

}
