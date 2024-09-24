package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.domain.InquiryAnswer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryAnswerModifyResponse {
    private Long id;
    private String title;
    private String content;

    public static InquiryAnswerModifyResponse from(InquiryAnswer inquiryAnswer) {
        InquiryAnswerModifyResponse response = new InquiryAnswerModifyResponse();
        response.setId(inquiryAnswer.getId());
        response.setTitle(inquiryAnswer.getTitle());
        response.setContent(inquiryAnswer.getContent());
        return response;
    }
}
