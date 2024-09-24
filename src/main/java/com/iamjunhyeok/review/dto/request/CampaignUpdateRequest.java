package com.iamjunhyeok.review.dto.request;

import com.iamjunhyeok.review.dto.CampaignBaseDto;
import com.iamjunhyeok.review.dto.CampaignLinkDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CampaignUpdateRequest extends CampaignBaseDto {
    Long typeCodeId;
    Long categoryCodeId;
    Long socialCodeId;

    List<CampaignLinkDto> links;

    List<String> deleteImageNames;
}
