package com.iamjunhyeok.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignApplyRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    private String address;

    private String rest;

    private String postalCode;

    public static CampaignApplyRequest of(String name, String phoneNumber) {
        CampaignApplyRequest request = new CampaignApplyRequest();
        request.setName(name);
        request.setPhoneNumber(phoneNumber);
        return request;
    }

    public static CampaignApplyRequest of(String name, String phoneNumber, String address, String rest, String postalCode) {
        CampaignApplyRequest request = new CampaignApplyRequest();
        request.setName(name);
        request.setPhoneNumber(phoneNumber);
        request.setAddress(address);
        request.setRest(rest);
        request.setPostalCode(postalCode);
        return request;
    }
}
