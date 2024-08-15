package com.iamjunhyeok.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointWithdrawalRequest {

    @NotBlank
    private String bank;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String accountHolder;

    @NotBlank
    private String idNumber;

    @Positive
    private int amount;
}
