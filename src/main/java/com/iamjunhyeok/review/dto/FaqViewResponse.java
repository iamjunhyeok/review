package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Faq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqViewResponse {
    private String question;
    private String answer;

    public static FaqViewResponse from(Faq faq) {
        FaqViewResponse response = new FaqViewResponse();
        response.setQuestion(faq.getQuestion());
        response.setAnswer(faq.getAnswer());
        return response;
    }
}
