package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.domain.Code;

@EntityView(Code.class)
public interface CodeProjection {
    Long getId();

    String getCode();

    String getValue();

    Integer getOrder();

    boolean getDeleted();
}
