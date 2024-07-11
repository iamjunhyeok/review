package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CampaignUpdateRequest extends CampaignBaseDto {
    List<CampaignLinkDto> links = new ArrayList<>();

    List<Long> deleteLinkIds = new ArrayList<>();
    List<Long> deleteImageIds = new ArrayList<>();
}
