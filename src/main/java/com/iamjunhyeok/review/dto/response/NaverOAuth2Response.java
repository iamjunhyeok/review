package com.iamjunhyeok.review.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverOAuth2Response {
    @JsonProperty("resultcode")
    private String resultCode;
    private String message;
    private Details response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Details {
        private String id;
        private String nickname;
        private String name;
        private String email;
        private String mobile;
    }
}
