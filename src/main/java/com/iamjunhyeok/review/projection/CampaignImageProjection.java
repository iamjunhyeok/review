package com.iamjunhyeok.review.projection;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class CampaignImageProjection {
    private Long id;
    private String name;
}