package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicantSearchResponse {
    private String nickname;
    private LocalDateTime createdDate;

    public static ApplicantSearchResponse from(User user) {
        ApplicantSearchResponse response = new ApplicantSearchResponse();
        response.setNickname(user.getNickname());
        return response;
    }
}
