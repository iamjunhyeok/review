package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.FaqCategory;
import com.iamjunhyeok.review.domain.Faq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqSearchResponse {
    private Long id;
    private FaqCategory category;
    private String question;
    private String answer;

    public static FaqSearchResponse from(Faq faq) {
        FaqSearchResponse response = new FaqSearchResponse();
        response.setId(faq.getId());
        response.setCategory(faq.getCategory());
        response.setQuestion(faq.getQuestion());
        response.setAnswer(faq.getAnswer());
        return response;
    }
}
