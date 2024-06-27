package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.InquiryCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryCreateRequest {

    @NotNull
    private InquiryCategory category;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
