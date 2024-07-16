package com.iamjunhyeok.review.dto;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.iamjunhyeok.review.domain.CampaignMission;

@EntityView(CampaignMission.class)
public interface CampaignMissionProjection {
    @IdMapping
    Long getId();

    String getArguments();

    @Mapping("code.value")
    String getValue();
}
