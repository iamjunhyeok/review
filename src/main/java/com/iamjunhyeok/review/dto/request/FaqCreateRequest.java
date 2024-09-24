package com.iamjunhyeok.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqCreateRequest {

    @NotNull
    private Long categoryCodeId;

    @NotBlank
    private String question;

    @NotBlank
    private String answer;
}
