package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
}
