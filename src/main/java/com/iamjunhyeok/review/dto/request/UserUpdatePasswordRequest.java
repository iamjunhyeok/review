package com.iamjunhyeok.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdatePasswordRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmNewPassword;

    public static UserUpdatePasswordRequest of(String oldPassword, String newPassword) {
        UserUpdatePasswordRequest userUpdatePasswordRequest = new UserUpdatePasswordRequest();
        userUpdatePasswordRequest.setCurrentPassword(oldPassword);
        userUpdatePasswordRequest.setNewPassword(newPassword);
        return userUpdatePasswordRequest;
    }
}
