package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.CampaignMission;

@EntityView(CampaignMission.class)
public interface CampaignMissionView {
    @IdMapping
    Long getId();

    String getValue();

    CodeView getCode();
}
