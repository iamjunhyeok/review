package com.iamjunhyeok.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertiserApplyRequest {
    private String businessTypeCode;
    private String businessNumber;
    private String businessName;
    private String homepageUrl;
    private String taxInvoiceEmail;
    private String accessSourceCode;
}