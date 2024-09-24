package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.constant.InquiryCategory;
import com.iamjunhyeok.review.domain.Inquiry;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InquirySearchResponse {
    private Long id;
    private InquiryCategory category;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static InquirySearchResponse from(Inquiry inquiry) {
        InquirySearchResponse response = new InquirySearchResponse();
        response.setId(inquiry.getId());
        response.setTitle(inquiry.getTitle());
        response.setContent(inquiry.getContent());
        response.setCreatedAt(inquiry.getCreatedAt());
        response.setUpdatedAt(inquiry.getUpdatedAt());
        return response;
    }
}
