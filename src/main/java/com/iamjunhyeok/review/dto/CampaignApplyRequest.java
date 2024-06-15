package com.iamjunhyeok.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignApplyRequest {

    @NotBlank
    private String name;

    private String address;

    private String zipCode;

    @NotBlank
    private String phoneNumber;

    public static CampaignApplyRequest of(String name, String phoneNumber) {
        CampaignApplyRequest request = new CampaignApplyRequest();
        request.setName(name);
        request.setPhoneNumber(phoneNumber);
        return request;
    }
}
