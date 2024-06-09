package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinResponse {
    private String email;
    private String password;

    public static UserJoinResponse from(UserDto userDto) {
        UserJoinResponse userJoinResponse = new UserJoinResponse();
        userJoinResponse.setEmail(userDto.getEmail());
        userJoinResponse.setPassword(userDto.getPassword());
        return userJoinResponse;
    }
}
