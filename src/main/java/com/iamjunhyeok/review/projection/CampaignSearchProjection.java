package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignType;
import com.iamjunhyeok.review.domain.Campaign;

import java.time.LocalDate;
import java.util.List;

@EntityView(Campaign.class)
public interface CampaignSearchProjection {
    @IdMapping
    Long getId();

    CampaignType getType();
    CampaignCategory getCategory();
    CampaignSocial getSocial();

    String getTitle();
    Integer getCapacity();

    LocalDate getApplicationEndDate();

    String getOfferingSummary();

    @Mapping("size(applications)")
    Long getApplicantsCount();

    String getLongitude();
    String getLatitude();

    List<CampaignImageProjection> getImages();

}
