package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Answer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerUpdateResponse {
    private Long id;
    private String title;
    private String content;

    public static AnswerUpdateResponse from(Answer answer) {
        AnswerUpdateResponse response = new AnswerUpdateResponse();
        response.setId(answer.getId());
        response.setTitle(answer.getTitle());
        response.setContent(answer.getContent());
        return response;
    }
}
