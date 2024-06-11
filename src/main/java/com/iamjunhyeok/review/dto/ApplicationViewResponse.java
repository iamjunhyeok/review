package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Application;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationViewResponse {
    private String name;
    private String phoneNumber;

    public static ApplicationViewResponse from(Application application) {
        ApplicationViewResponse response = new ApplicationViewResponse();
        response.setName(application.getName());
        response.setPhoneNumber(application.getPhoneNumber());
        return response;
    }
}
