package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.WithdrawalWithUserProjection;

import java.util.List;

public interface CustomWithdrawalRepository {
    List<WithdrawalWithUserProjection> fetchAllWithdrawalHistory();

}
