package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.constant.WithdrawalStatus;
import com.iamjunhyeok.review.domain.Withdrawal;

import java.time.LocalDateTime;

@EntityView(Withdrawal.class)
public interface WithdrawalProjection {
    Long getId();

    String getBank();

    String getAccountNumber();

    String getAccountHolder();

    String getIdNumber();

    int getAmount();

    WithdrawalStatus getStatus();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
