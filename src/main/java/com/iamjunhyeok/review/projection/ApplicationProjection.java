package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.domain.Application;

@EntityView(Application.class)
public interface ApplicationProjection {
    Long getId();

    String getName();

    String getPhoneNumber();

    String getAddress();

    String getRest();

    String getPostalCode();

    ApplicationStatus getStatus();
}
