package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.constant.CampaignType;
import com.iamjunhyeok.review.domain.Campaign;

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

    String getOfferingSummary();

    String getKeyword();

    String getHashtag();

    String getGuide();

    String getInformation();

    CampaignStatus getStatus();

    String getStoreInformation();

    Integer getPoint();

    String getAddress();

    String getRest();

    String getPostalCode();

    String getLongitude();

    String getLatitude();

    List<CampaignLinkProjection> getLinks();

    List<CampaignImageProjection> getImages();

    List<CampaignMissionProjection> getMissions();

    List<CampaignOptionProjection> getOptions();

    @Mapping("size(applications)")
    Long getApplicantsCount();
}
