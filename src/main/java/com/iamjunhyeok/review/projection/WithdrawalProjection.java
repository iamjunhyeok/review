package com.iamjunhyeok.review.projection;

import com.iamjunhyeok.review.constant.WithdrawalStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WithdrawalProjection {
    private Long id;
    private String bank;
    private String accountNumber;
    private String accountHolder;
    private String idNumber;
    private int amount;
    private WithdrawalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
