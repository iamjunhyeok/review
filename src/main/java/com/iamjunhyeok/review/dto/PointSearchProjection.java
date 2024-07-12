package com.iamjunhyeok.review.dto;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.PointReason;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.Point;

import java.time.LocalDateTime;

@EntityView(Point.class)
public interface PointSearchProjection {
    @IdMapping
    Long getId();

    Integer getAmount();

    PointReason getReason();

    LocalDateTime getCreatedAt();

    ApplicationProjection getApplication();

    @EntityView(Application.class)
    interface ApplicationProjection {
        CampaignProjection getCampaign();

        @EntityView(Campaign.class)
        interface CampaignProjection {
            Long getId();

            String getTitle();
        }
    }
}
