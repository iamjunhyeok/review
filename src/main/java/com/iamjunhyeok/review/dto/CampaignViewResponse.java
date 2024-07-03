package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.CampaignBase;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CampaignViewResponse extends CampaignBase {
    private Long id;
    private List<String> links = new ArrayList<>();
    private List<String> imageNames = new ArrayList<>();

}
