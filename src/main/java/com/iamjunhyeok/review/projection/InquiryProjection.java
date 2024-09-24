package com.iamjunhyeok.review.projection;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryProjection {
    private Long id;
    private CodeProjection categoryCode;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserProjection user;
    private InquiryAnswerProjection answer;
}
