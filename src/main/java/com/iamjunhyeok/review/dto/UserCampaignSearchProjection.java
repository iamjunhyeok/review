package com.iamjunhyeok.review.dto;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignType;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Campaign;

import java.time.LocalDate;

@EntityView(Campaign.class)
public interface UserCampaignSearchProjection {
    CampaignType getType();

    CampaignSocial getSocial();

    String getTitle();

    LocalDate getAnnouncementDate();

    LocalDate getReviewStartDate();

    LocalDate getReviewEndDate();

    ApplicationProjection getApplications();

    @EntityView(Application.class)
    interface ApplicationProjection {
        Long getId();

        ApplicationStatus getStatus();
    }
}
