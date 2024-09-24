package com.iamjunhyeok.review.projection;

import lombok.Getter;

@Getter
public class FaqProjection {
    private Long id;
    private CodeProjection categoryCode;
    private String question;
    private String answer;
}
