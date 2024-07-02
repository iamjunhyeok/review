package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.InquiryCategory;
import com.iamjunhyeok.review.domain.Inquiry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryCreateResponse {
    private Long id;
    private InquiryCategory category;
    private String title;
    private String content;

    public static InquiryCreateResponse from(Inquiry inquiry) {
        InquiryCreateResponse response = new InquiryCreateResponse();
        response.setId(inquiry.getId());
        response.setCategory(inquiry.getCategory());
        response.setTitle(inquiry.getTitle());
        response.setContent(inquiry.getContent());
        return response;
    }
}
