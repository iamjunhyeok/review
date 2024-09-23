package com.iamjunhyeok.review.projection;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class CampaignLinkProjection {
    private Long id;
    private String url;
}
