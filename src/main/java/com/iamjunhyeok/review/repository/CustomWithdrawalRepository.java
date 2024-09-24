package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.WithdrawalProjection;
import com.iamjunhyeok.review.projection.WithdrawalWithUserProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomWithdrawalRepository {
    List<WithdrawalWithUserProjection> fetchAllWithdrawalHistory();

    List<WithdrawalProjection> fetchAllWithdrawalHistoryByUserId(Long userId, Pageable pageable);
}
