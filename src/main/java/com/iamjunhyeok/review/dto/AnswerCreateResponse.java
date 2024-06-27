package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Answer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerCreateResponse {
    private Long id;
    private String title;
    private String content;

    public static AnswerCreateResponse from(Answer answer) {
        AnswerCreateResponse response = new AnswerCreateResponse();
        response.setId(answer.getId());
        response.setTitle(answer.getTitle());
        response.setContent(answer.getContent());
        return response;
    }
}
