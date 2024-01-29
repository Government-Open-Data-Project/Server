package com.springboot.government_data_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewsResponseDTO {
    @JsonProperty("nzdppcljavkxnylqs")
    private List<Item> itemList;
    @Data
    private class Item {
        @JsonProperty("head")
        private Head head;
        @JsonProperty("row")
        private List<Row> row;

        @Data
        private class Head {
            @JsonProperty("list_total_count")
            private int listTotalCount;
            @JsonProperty("RESULT")
            private Result result;


            @Data
            private class Result {
                @JsonProperty("CODE")
                private String code;
                @JsonProperty("MESSAGE")
                private String message;
            }


        }

        @Data
        private class Row {
            @JsonProperty("COMP_MAIN_TITLE")
            private String compMainTitle;

            @JsonProperty("REG_DATE")
            private String regDate;

            @JsonProperty("COMP_CONTENT")
            private String compContent;

            @JsonProperty("LINK_URL")
            private String linkUrl;
        }
    }
}
