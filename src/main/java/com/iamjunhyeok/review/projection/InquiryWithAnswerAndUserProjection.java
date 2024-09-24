package com.iamjunhyeok.review.projection;

import com.iamjunhyeok.review.constant.InquiryCategory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryWithAnswerAndUserProjection {
    private Long id;
    private InquiryCategory category;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserSimpleView user;
    private InquiryAnswerProjection inquiryAnswer;
}
