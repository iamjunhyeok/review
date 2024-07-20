package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.CampaignOption;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignOptionDto {
    private Long id;
    private CodeDto code;

    public static CampaignOptionDto from(CampaignOption campaignOption) {
        CampaignOptionDto campaignOptionDto = new CampaignOptionDto();
        campaignOptionDto.setId(campaignOption.getId());
        return campaignOptionDto;
    }
}
