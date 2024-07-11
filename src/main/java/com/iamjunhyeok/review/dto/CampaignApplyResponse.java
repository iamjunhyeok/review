package com.iamjunhyeok.review.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iamjunhyeok.review.domain.Application;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignApplyResponse {
    private Long id;
    private String name;
    private String phoneNumber;
    private String address;
    private String rest;
    private String postalCode;

    public static CampaignApplyResponse from(Application application) {
        return CampaignApplyResponse
                .builder()
                .id(application.getId())
                .name(application.getName())
                .phoneNumber(application.getPhoneNumber())
                .address(application.getAddress())
                .rest(application.getRest())
                .postalCode(application.getPostalCode())
                .build();
    }

}
