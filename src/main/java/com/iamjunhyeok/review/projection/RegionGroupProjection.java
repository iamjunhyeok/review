package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.RegionGroup;

@EntityView(RegionGroup.class)
public interface RegionGroupProjection {

    @IdMapping
    Long getId();

    String getName();

    String getDescription();

    int getOrder();

    boolean getDeleted();
}
