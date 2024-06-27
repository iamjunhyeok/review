package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.InquiryCategory;
import com.iamjunhyeok.review.domain.Inquiry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryUpdateResponse {
    private InquiryCategory category;
    private String title;
    private String content;

    public static InquiryUpdateResponse from(Inquiry inquiry) {
        InquiryUpdateResponse response = new InquiryUpdateResponse();
        response.setCategory(inquiry.getCategory());
        response.setTitle(inquiry.getTitle());
        response.setContent(inquiry.getContent());
        return response;
    }
}
