package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinRequest {
    private String email;
    private String nickname;
    private String password;
    private String confirmPassword;

    public static UserJoinRequest of(String email, String password) {
        UserJoinRequest request = new UserJoinRequest();
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }
}
