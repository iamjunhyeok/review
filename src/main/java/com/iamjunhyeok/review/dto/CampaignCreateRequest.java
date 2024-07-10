package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CampaignCreateRequest extends CampaignBaseDto {
    private List<CampaignLinkDto> links = new ArrayList<>();
}
