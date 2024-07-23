package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.domain.CampaignBase;
import com.iamjunhyeok.review.dto.CampaignImageDto;
import com.iamjunhyeok.review.dto.CampaignLinkDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CampaignViewResponse extends CampaignBase {
    private Long id;
    private List<CampaignLinkDto> links = new ArrayList<>();
    private List<CampaignImageDto> images = new ArrayList<>();
}
