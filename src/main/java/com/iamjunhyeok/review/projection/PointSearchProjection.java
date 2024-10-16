package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.PointReason;
import com.iamjunhyeok.review.domain.Point;

import java.time.LocalDateTime;

@EntityView(Point.class)
public interface PointSearchProjection {
    @IdMapping
    Long getId();

    Integer getAmount();

    PointReason getReason();

    LocalDateTime getCreatedAt();

    ApplicationCampaignProjection getApplication();
}
