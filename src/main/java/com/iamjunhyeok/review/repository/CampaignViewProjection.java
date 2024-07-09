package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.constant.CampaignType;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.dto.CampaignImageProjection;
import com.iamjunhyeok.review.dto.CampaignLinkProjection;

import java.time.LocalDate;
import java.util.List;

@EntityView(Campaign.class)
public interface CampaignViewProjection {
    @IdMapping
    Long getId();

    CampaignType getType();

    CampaignCategory getCategory();

    CampaignSocial getSocial();

    String getTitle();

    Integer getCapacity();

    LocalDate getApplicationStartDate();

    LocalDate getApplicationEndDate();

    LocalDate getAnnouncementDate();

    LocalDate getReviewStartDate();

    LocalDate getReviewEndDate();

    String getOffering();

    String getKeyword();

    String getHashtag();

    String getMission();

    String getGuide();

    String getInformation();

    CampaignStatus getStatus();

    String getAddress();

    String getRest();

    String getPostalCode();

    String getLongitude();

    String getLatitude();

    List<CampaignLinkProjection> getLinks();

    List<CampaignImageProjection> getImages();
}
