package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.domain.Application;

import java.util.List;

@EntityView(Application.class)
public interface ApplicationSearchProjection {
    @IdMapping
    Long getId();

    ApplicationStatus getStatus();

    CampaignSummaryProjection getCampaign();

    List<ReviewStatusProjection> getReviews();
}
