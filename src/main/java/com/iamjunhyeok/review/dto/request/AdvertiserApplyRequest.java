package com.iamjunhyeok.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertiserApplyRequest {
    private Long businessTypeCodeId;
    private String businessNumber;
    private String businessName;
    private String homepageUrl;
    private String taxInvoiceEmail;
}