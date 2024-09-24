package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.constant.InquiryCategory;
import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.domain.InquiryAnswer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryViewResponse {
    private Long id;
    private InquiryCategory category;
    private String title;
    private String content;
    private AnswerResponse answer;

    public static InquiryViewResponse from(Inquiry inquiry) {
        InquiryViewResponse response = new InquiryViewResponse();
        response.setId(inquiry.getId());
        response.setTitle(inquiry.getTitle());
        response.setContent(inquiry.getContent());
        if (inquiry.getInquiryAnswer() != null) {
            response.setAnswer(AnswerResponse.from(inquiry.getInquiryAnswer()));
        }
        return response;
    }

    @Getter
    @Setter
    private static class AnswerResponse {
        private Long id;
        private String title;
        private String content;

        public static AnswerResponse from(InquiryAnswer inquiryAnswer) {
            AnswerResponse response = new AnswerResponse();
            response.setId(inquiryAnswer.getId());
            response.setTitle(inquiryAnswer.getTitle());
            response.setContent(inquiryAnswer.getContent());
            return response;
        }
    }
}
