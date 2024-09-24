package com.iamjunhyeok.review.projection;

import com.iamjunhyeok.review.constant.ApplicationStatus;
import lombok.Getter;

@Getter
public class ApplicantProjection {
    private Long id;
    private String name;
    private String phoneNumber;
    private String address;
    private String rest;
    private String postalCode;
    private ApplicationStatus status;

    private int penaltyPoints;
}
