package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.CampaignLink;
import com.iamjunhyeok.review.dto.CampaignBaseDto;
import com.iamjunhyeok.review.dto.CampaignMissionDto;
import com.iamjunhyeok.review.dto.CampaignOptionDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class CampaignCreateResponse extends CampaignBaseDto {
    private Long id;
    private List<String> links;

    public static CampaignCreateResponse from(Campaign campaign) {
        return CampaignCreateResponse.builder()
                .id(campaign.getId())
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
                .guide(campaign.getGuide())
                .information(campaign.getInformation())
                .address(campaign.getAddress())
                .rest(campaign.getRest())
                .postalCode(campaign.getPostalCode())
                .longitude(campaign.getLongitude())
                .latitude(campaign.getLatitude())
                .status(campaign.getStatus())
                .storeInformation(campaign.getStoreInformation())
                .point(campaign.getPoint())
                .links(
                        campaign.getLinks()
                                .stream().map(CampaignLink::getUrl)
                                .toList()
                )
                .missions(
                        campaign.getMissions()
                                .stream().map(CampaignMissionDto::from)
                                .toList()
                )
                .options(
                        campaign.getOptions()
                                .stream().map(CampaignOptionDto::from)
                                .toList()
                )
                .build();
    }
}
