package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Withdrawal;
import com.iamjunhyeok.review.projection.WithdrawalProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long>, CustomWithdrawalRepository {

    @Query("select w from Withdrawal w where w.user.id = :userId")
    List<WithdrawalProjection> fetchAllWithdrawalHistoryByUserId(Long userId);

}
