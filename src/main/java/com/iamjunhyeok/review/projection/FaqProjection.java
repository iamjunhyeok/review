package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.FaqCategory;
import com.iamjunhyeok.review.domain.Faq;

@EntityView(Faq.class)
public interface FaqProjection {
    @IdMapping
    Long getId();

    FaqCategory getCategory();

    String getQuestion();

    String getAnswer();
}
