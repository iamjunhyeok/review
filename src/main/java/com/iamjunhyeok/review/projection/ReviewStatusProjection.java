package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.constant.ReviewStatus;
import com.iamjunhyeok.review.domain.Review;

@EntityView(Review.class)
public interface ReviewStatusProjection {
    ReviewStatus getStatus();
}
