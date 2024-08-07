package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.InquiryAnswer;

@EntityView(InquiryAnswer.class)
public interface InquiryAnswerProjection {
    @IdMapping
    Long getId();

    String getTitle();

    String getContent();
}
