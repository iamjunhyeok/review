package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.ReviewStatus;
import com.iamjunhyeok.review.constant.ReviewType;
import com.iamjunhyeok.review.domain.Review;

@EntityView(Review.class)
public interface ReviewProjection {
    @IdMapping
    Long getId();

    ReviewType getType();

    String getUrl();

    ReviewStatus getStatus();

    String getDetails();
}
