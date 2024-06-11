package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordRequest {
    private String oldPassword;
    private String newPassword;

    public static UserChangePasswordRequest of(String oldPassword, String newPassword) {
        UserChangePasswordRequest userChangePasswordRequest = new UserChangePasswordRequest();
        userChangePasswordRequest.setOldPassword(oldPassword);
        userChangePasswordRequest.setNewPassword(newPassword);
        return userChangePasswordRequest;
    }
}
