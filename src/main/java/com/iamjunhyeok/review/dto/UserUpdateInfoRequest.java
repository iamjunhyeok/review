package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.Gender;
import com.iamjunhyeok.review.domain.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateInfoRequest extends Address {

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    private String name;

    private String phoneNumber;

    private Gender gender;

    private LocalDate birthDate;

    private String profileImageName;
}
