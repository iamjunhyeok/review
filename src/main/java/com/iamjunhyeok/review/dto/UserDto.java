package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
