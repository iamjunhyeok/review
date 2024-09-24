package com.iamjunhyeok.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryUpdateRequest {

    @NotNull
    private Long categoryCodeId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
