package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.domain.Campaign;

@EntityView(Campaign.class)
public interface CampaignView {
    Long getId();

    String getTitle();
}
