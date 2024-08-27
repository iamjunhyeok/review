package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.Notice;

@EntityView(Notice.class)
public interface NoticeProjection {
    @IdMapping
    Long getId();

    String getCategoryCode();

    String getTitle();

    String getContent();
}
