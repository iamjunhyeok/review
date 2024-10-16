package com.iamjunhyeok.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryAnswerRegisterRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
