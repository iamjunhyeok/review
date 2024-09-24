package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.constant.FaqCategory;
import com.iamjunhyeok.review.domain.Faq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqCreateResponse {
    private Long id;
    private FaqCategory category;
    private String question;
    private String answer;

    public static FaqCreateResponse from(Faq faq) {
        FaqCreateResponse response = new FaqCreateResponse();
        response.setId(faq.getId());
        response.setQuestion(faq.getQuestion());
        response.setAnswer(faq.getAnswer());
        return response;
    }
}
