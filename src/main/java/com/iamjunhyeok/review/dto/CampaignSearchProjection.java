package com.iamjunhyeok.review.dto;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignType;
import com.iamjunhyeok.review.domain.Campaign;

import java.time.LocalDate;

@EntityView(Campaign.class)
public interface CampaignSearchProjection {
    @IdMapping
    Long getId();

    CampaignType getType();
    CampaignSocial getSocial();

    String getTitle();
    Integer getCapacity();

    LocalDate getApplicationEndDate();

    @Mapping("size(applications)")
    Long getApplicantsCount();

    String getLongitude();
    String getLatitude();
}
