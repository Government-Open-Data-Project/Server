package com.springboot.government_data_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor // Jackson 역직렬화를 위한 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 포함하는 생성자 추가
public class NewsResponseDTO {
    @JsonProperty("nzdppcljavkxnylqs")
    private List<Item> itemList;

    @Data
    @NoArgsConstructor // 내부 클래스 역직렬화를 위한 기본 생성자 추가
    @AllArgsConstructor // 모든 필드를 포함하는 생성자 추가
    public static class Item {
        @JsonProperty("head")
        private Head head;
        @JsonProperty("row")
        private List<Row> row;

        @Data
        @NoArgsConstructor // 내부 클래스 역직렬화를 위한 기본 생성자 추가
        @AllArgsConstructor // 모든 필드를 포함하는 생성자 추가
        public static class Head {
            @JsonProperty("list_total_count")
            private int listTotalCount;
            @JsonProperty("RESULT")
            private Result result;

            @Data
            @NoArgsConstructor // 내부 클래스 역직렬화를 위한 기본 생성자 추가
            @AllArgsConstructor // 모든 필드를 포함하는 생성자 추가
            public static class Result {
                @JsonProperty("CODE")
                private String code;
                @JsonProperty("MESSAGE")
                private String message;
            }
        }

        @Data
        @NoArgsConstructor // 내부 클래스 역직렬화를 위한 기본 생성자 추가
        @AllArgsConstructor // 모든 필드를 포함하는 생성자 추가
        public static class Row {
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
