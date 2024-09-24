package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.Banner;

@EntityView(Banner.class)
public interface BannerSimpleView {
    @IdMapping
    Long getId();

    String getImageName();

    String getLinkUrl();
}
