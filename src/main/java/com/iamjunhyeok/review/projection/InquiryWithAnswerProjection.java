package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.InquiryCategory;
import com.iamjunhyeok.review.domain.Inquiry;

import java.time.LocalDateTime;

@EntityView(Inquiry.class)
public class InquiryWithAnswerProjection {

    @IdMapping
    private Long id;

    private InquiryCategory category;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private InquiryAnswerProjection inquiryAnswer;
}
