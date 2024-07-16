package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CampaignUpdateRequest extends CampaignBaseDto {
    List<CampaignLinkDto> links;

    List<Long> deleteLinkIds;
    List<Long> deleteImageIds;

    List<Long> deleteMissionIds;
    List<Long> deleteOptionIds;
}
