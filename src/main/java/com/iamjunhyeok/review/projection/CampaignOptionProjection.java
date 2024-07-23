package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.CampaignOption;

@EntityView(CampaignOption.class)
public interface CampaignOptionProjection {
    @IdMapping
    Long getId();

    CodeProjection getCode();
}
