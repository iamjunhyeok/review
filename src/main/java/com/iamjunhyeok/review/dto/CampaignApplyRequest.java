package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignApplyRequest {
    private String name;
    private String phoneNumber;

    public static CampaignApplyRequest of(String name, String phoneNumber) {
        CampaignApplyRequest request = new CampaignApplyRequest();
        request.setName(name);
        request.setPhoneNumber(phoneNumber);
        return request;
    }
}
