package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.PenaltyReason;
import com.iamjunhyeok.review.domain.Penalty;

@EntityView(Penalty.class)
public interface PenaltyProjection {
    @IdMapping
    Long getId();

    PenaltyReason getReason();

    String getDetails();

    int getPoint();

    ApplicationCampaignProjection getApplication();
}
