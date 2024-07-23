package com.iamjunhyeok.review.dto.request;

import com.iamjunhyeok.review.constant.FaqCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqCreateRequest {

    @NotNull
    private FaqCategory category;

    @NotBlank
    private String question;

    @NotBlank
    private String answer;
}
