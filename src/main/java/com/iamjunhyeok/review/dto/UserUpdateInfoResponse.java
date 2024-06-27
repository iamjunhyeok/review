package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.Gender;
import com.iamjunhyeok.review.domain.Address;
import com.iamjunhyeok.review.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class UserUpdateInfoResponse extends Address {
    private String email;

    private String nickname;

    private String name;

    private String phoneNumber;

    private Gender gender;

    private LocalDate birthDate;

    public static UserUpdateInfoResponse from(User user) {
        return UserUpdateInfoResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .rest(user.getRest())
                .postalCode(user.getPostalCode())
                .build();
    }
}
