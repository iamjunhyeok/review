package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long>, CustomWithdrawalRepository {

}
