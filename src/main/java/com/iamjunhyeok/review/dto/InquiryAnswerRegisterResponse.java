package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.InquiryAnswer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryAnswerRegisterResponse {
    private Long id;
    private String title;
    private String content;

    public static InquiryAnswerRegisterResponse from(InquiryAnswer inquiryAnswer) {
        InquiryAnswerRegisterResponse response = new InquiryAnswerRegisterResponse();
        response.setId(inquiryAnswer.getId());
        response.setTitle(inquiryAnswer.getTitle());
        response.setContent(inquiryAnswer.getContent());
        return response;
    }
}
