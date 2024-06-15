package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqCreateResponse {
    private String question;
    private String answer;

    public FaqCreateResponse(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
