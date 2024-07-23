package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.constant.FaqCategory;
import com.iamjunhyeok.review.domain.Faq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqUpdateResponse {
    private Long id;
    private FaqCategory category;
    private String question;
    private String answer;

    public static FaqUpdateResponse from(Faq faq) {
        FaqUpdateResponse response = new FaqUpdateResponse();
        response.setId(faq.getId());
        response.setCategory(faq.getCategory());
        response.setQuestion(faq.getQuestion());
        response.setAnswer(faq.getAnswer());
        return response;
    }
}
