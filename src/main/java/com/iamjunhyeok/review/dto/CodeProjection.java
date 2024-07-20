package com.iamjunhyeok.review.dto;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.domain.Code;

@EntityView(Code.class)
public interface CodeProjection {
    Long getId();

    String getCode();

    String getValue();
}
