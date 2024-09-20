package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Campaign;

import java.time.LocalDate;
import java.util.List;

@EntityView(Campaign.class)
public interface UserCampaignSearchProjection {
    @IdMapping
    Long getId();

    CodeProjection getTypeCode();

    CodeProjection getCategoryCode();

    CodeProjection getSocialCode();

    String getTitle();

    LocalDate getAnnouncementDate();

    LocalDate getReviewStartDate();

    LocalDate getReviewEndDate();

    ApplicationView getApplications();

    CampaignStatus getStatus();

    List<CampaignImageProjection> getImages();
}
