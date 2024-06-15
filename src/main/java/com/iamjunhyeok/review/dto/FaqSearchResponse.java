package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Faq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqSearchResponse {
    private String question;
    private String answer;

    public static FaqSearchResponse from(Faq faq) {
        FaqSearchResponse response = new FaqSearchResponse();
        response.setQuestion(faq.getQuestion());
        response.setAnswer(faq.getAnswer());
        return response;
    }
}
