package com.iamjunhyeok.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmNewPassword;

    public static UserChangePasswordRequest of(String oldPassword, String newPassword) {
        UserChangePasswordRequest userChangePasswordRequest = new UserChangePasswordRequest();
        userChangePasswordRequest.setCurrentPassword(oldPassword);
        userChangePasswordRequest.setNewPassword(newPassword);
        return userChangePasswordRequest;
    }
}
