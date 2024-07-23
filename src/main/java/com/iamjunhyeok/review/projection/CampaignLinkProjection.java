package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.CampaignLink;

@EntityView(CampaignLink.class)
public interface CampaignLinkProjection {
    @IdMapping
    Long getId();

    String getUrl();
}
