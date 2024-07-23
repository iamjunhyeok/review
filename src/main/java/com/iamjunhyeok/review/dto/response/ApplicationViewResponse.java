package com.iamjunhyeok.review.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iamjunhyeok.review.constant.ApplicationReason;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.domain.Application;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationViewResponse {
    private Long id;
    private String name;
    private String phoneNumber;
    private String address;
    private String rest;
    private String postalCode;

    private ApplicationStatus status;
    private ApplicationReason reason;
    private String details;

    public static ApplicationViewResponse from(Application application) {
        return ApplicationViewResponse
                .builder()
                .id(application.getId())
                .name(application.getName())
                .phoneNumber(application.getPhoneNumber())
                .address(application.getAddress())
                .rest(application.getRest())
                .postalCode(application.getPostalCode())
                .status(application.getStatus())
                .reason(application.getReason())
                .details(application.getDetails())
                .build();
    }
}
