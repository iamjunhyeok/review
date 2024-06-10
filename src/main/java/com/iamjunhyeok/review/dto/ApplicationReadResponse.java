package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Application;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationReadResponse {
    private String name;
    private String phoneNumber;

    public static ApplicationReadResponse from(Application application) {
        ApplicationReadResponse response = new ApplicationReadResponse();
        response.setName(application.getName());
        response.setPhoneNumber(application.getPhoneNumber());
        return response;
    }
}
