package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.domain.Application;

@EntityView(Application.class)
public interface ApplicationCampaignProjection {
    CampaignProjection getCampaign();
}
