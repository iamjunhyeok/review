package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.CampaignImage;

@EntityView(CampaignImage.class)
public interface CampaignImageView {
    @IdMapping
    Long getId();

    String getName();
}
