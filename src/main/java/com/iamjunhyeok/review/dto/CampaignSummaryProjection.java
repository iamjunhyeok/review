package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignType;

import java.time.LocalDate;

public interface CampaignSummaryProjection {
    Long getId();

    CampaignType getType();

    CampaignCategory getCategory();

    CampaignSocial getSocial();

    String getTitle();

    LocalDate getAnnouncementDate();

    LocalDate getReviewStartDate();

    LocalDate getReviewEndDate();
}
