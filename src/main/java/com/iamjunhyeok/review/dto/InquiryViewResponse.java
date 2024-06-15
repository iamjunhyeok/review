package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Inquiry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryViewResponse {
    private String title;
    private String content;

    public static InquiryViewResponse from(Inquiry inquiry) {
        InquiryViewResponse response = new InquiryViewResponse();
        response.setTitle(inquiry.getTitle());
        response.setContent(inquiry.getContent());
        return response;
    }
}
