package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.Application;

@EntityView(Application.class)
public interface ApplicantProjection {
    @IdMapping
    Long getId();

    String getName();

    String getPhoneNumber();

    String getAddress();

    String getRest();

    String getPostalCode();

    UserProjection getUser();
}
