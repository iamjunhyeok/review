package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Inquiry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquirySearchResponse {
    private String title;
    private String content;

    public static InquirySearchResponse from(Inquiry inquiry) {
        InquirySearchResponse response = new InquirySearchResponse();
        response.setTitle(inquiry.getTitle());
        response.setContent(inquiry.getContent());
        return response;
    }
}
