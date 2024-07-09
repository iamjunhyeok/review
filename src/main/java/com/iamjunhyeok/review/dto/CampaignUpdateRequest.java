package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.CampaignBase;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CampaignUpdateRequest extends CampaignBase {
    List<CampaignLinkDto> links = new ArrayList<>();

    List<Long> deleteLinkIds = new ArrayList<>();
    List<Long> deleteImageIds = new ArrayList<>();
}
