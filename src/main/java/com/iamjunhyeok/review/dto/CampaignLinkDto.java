package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.CampaignLink;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignLinkDto {
    private Long id;
    private String url;

    public static CampaignLinkDto from(CampaignLink campaignLink) {
        CampaignLinkDto dto = new CampaignLinkDto();
        dto.setId(campaignLink.getId());
        dto.setUrl(campaignLink.getUrl());
        return dto;
    }

    public static CampaignLinkDto of(Long id, String url) {
        CampaignLinkDto dto = new CampaignLinkDto();
        dto.setId(id);
        dto.setUrl(url);
        return dto;
    }
}
