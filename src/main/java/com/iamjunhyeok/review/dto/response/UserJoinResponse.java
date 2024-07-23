package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinResponse {
    private String email;
    private String password;

    public static UserJoinResponse from(User user) {
        UserJoinResponse userJoinResponse = new UserJoinResponse();
        userJoinResponse.setEmail(user.getEmail());
        userJoinResponse.setPassword(user.getPassword());
        return userJoinResponse;
    }
}
