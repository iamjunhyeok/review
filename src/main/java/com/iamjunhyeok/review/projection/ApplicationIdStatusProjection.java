package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.domain.Application;

@EntityView(Application.class)
public interface ApplicationIdStatusProjection {
    Long getId();

    ApplicationStatus getStatus();
}
