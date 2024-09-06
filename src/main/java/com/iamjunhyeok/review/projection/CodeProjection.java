package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.Code;

@EntityView(Code.class)
public interface CodeProjection {
    @IdMapping
    Long getId();

    String getCode();

    String getValue();

    Integer getOrder();

    boolean getDeleted();
}
