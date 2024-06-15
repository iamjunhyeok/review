package com.iamjunhyeok.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqUpdateRequest {

    @NotBlank
    private String question;

    @NotBlank
    private String answer;
}
