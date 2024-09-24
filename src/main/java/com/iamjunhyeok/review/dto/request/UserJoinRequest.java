package com.iamjunhyeok.review.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinRequest {

    @Email
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    public static UserJoinRequest of(String email, String password) {
        UserJoinRequest request = new UserJoinRequest();
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }
}
