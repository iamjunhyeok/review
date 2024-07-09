package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignImageDto {
    private Long id;
    private String name;

    public static CampaignImageDto of(Long id, String name) {
        CampaignImageDto dto = new CampaignImageDto();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }
}
