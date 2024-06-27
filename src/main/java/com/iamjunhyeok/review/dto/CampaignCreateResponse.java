package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.CampaignBase;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class CampaignCreateResponse extends CampaignBase {

    public static CampaignCreateResponse from(Campaign campaign) {
        return CampaignCreateResponse.builder()
                .type(campaign.getType())
                .category(campaign.getCategory())
                .social(campaign.getSocial())
                .title(campaign.getTitle())
                .capacity(campaign.getCapacity())
                .applicationStartDate(campaign.getApplicationStartDate())
                .applicationEndDate(campaign.getApplicationEndDate())
                .announcementDate(campaign.getAnnouncementDate())
                .reviewStartDate(campaign.getReviewStartDate())
                .reviewEndDate(campaign.getReviewEndDate())
                .offering(campaign.getOffering())
                .keyword(campaign.getKeyword())
                .hashtag(campaign.getHashtag())
                .mission(campaign.getMission())
                .guide(campaign.getGuide())
                .information(campaign.getInformation())
                .status(campaign.getApplicationStartDate().isAfter(LocalDate.now()) ? CampaignStatus.PLANNED : CampaignStatus.ONGOING)
                .address(campaign.getAddress())
                .rest(campaign.getRest())
                .postalCode(campaign.getPostalCode())
                .longitude(campaign.getLongitude())
                .latitude(campaign.getLatitude())
                .status(campaign.getStatus())
                .build();
    }
}
