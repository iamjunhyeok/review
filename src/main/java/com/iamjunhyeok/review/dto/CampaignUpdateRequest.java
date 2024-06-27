package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.CampaignBase;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CampaignUpdateRequest extends CampaignBase {
    List<String> links = new ArrayList<>();
}
