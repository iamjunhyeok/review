package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.CampaignLink;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignLinkDto {
    private Long id;
    private String link;

    public static CampaignLinkDto from(CampaignLink campaignLink) {
        CampaignLinkDto dto = new CampaignLinkDto();
        dto.setId(campaignLink.getId());
        dto.setLink(campaignLink.getLink());
        return dto;
    }
}
