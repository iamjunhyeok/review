package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.CampaignMission;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignMissionDto {
    private Long id;
    private String arguments;

    public static CampaignMissionDto from(CampaignMission campaignMission) {
        CampaignMissionDto campaignMissionDto = new CampaignMissionDto();
        campaignMissionDto.setId(campaignMission.getId());
        campaignMissionDto.setArguments(campaignMission.getArguments());
        return campaignMissionDto;
    }
}
