package com.iamjunhyeok.review.dto.request;

import com.iamjunhyeok.review.dto.CampaignBaseDto;
import com.iamjunhyeok.review.dto.CampaignLinkDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCreateRequest extends CampaignBaseDto {
    Long typeCodeId;
    Long categoryCodeId;
    Long socialCodeId;

    private List<CampaignLinkDto> links;
}
