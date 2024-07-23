package com.iamjunhyeok.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryAnswerModifyRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
