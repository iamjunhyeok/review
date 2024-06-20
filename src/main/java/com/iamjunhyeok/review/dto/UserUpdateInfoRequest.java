package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateInfoRequest extends Address {

    @NotBlank
    private String nickname;
}
