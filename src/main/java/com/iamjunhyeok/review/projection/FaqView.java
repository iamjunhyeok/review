package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.Faq;

@EntityView(Faq.class)
public interface FaqView {
    @IdMapping
    Long getId();

    String getQuestion();

    String getAnswer();
}
