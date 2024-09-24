package com.iamjunhyeok.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertiserApplyRequest {

    @NotNull
    private Long businessTypeCodeId;

    @NotBlank
    private String businessNumber;

    @NotBlank
    private String businessName;

    private String homepageUrl;

    private String taxInvoiceEmail;
}