package com.iamjunhyeok.review.dto;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.CampaignMission;

@EntityView(CampaignMission.class)
public interface CampaignMissionProjection {
    @IdMapping
    Long getId();

    String getArguments();

    CodeProjection getCode();
}
