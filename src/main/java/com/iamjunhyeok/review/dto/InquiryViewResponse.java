package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Answer;
import com.iamjunhyeok.review.domain.Inquiry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryViewResponse {
    private String title;
    private String content;
    private AnswerResponse answer;

    public static InquiryViewResponse from(Inquiry inquiry) {
        InquiryViewResponse response = new InquiryViewResponse();
        response.setTitle(inquiry.getTitle());
        response.setContent(inquiry.getContent());
        if (inquiry.getAnswer() != null) {
            response.setAnswer(AnswerResponse.from(inquiry.getAnswer()));
        }
        return response;
    }

    @Getter
    @Setter
    private static class AnswerResponse {
        private String title;
        private String content;

        public static AnswerResponse from(Answer answer) {
            AnswerResponse response = new AnswerResponse();
            response.setTitle(answer.getTitle());
            response.setContent(answer.getContent());
            return response;
        }
    }
}
