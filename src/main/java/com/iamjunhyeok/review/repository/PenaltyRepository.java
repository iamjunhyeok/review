package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long>, CustomPenaltyRepository {
    Optional<Penalty> findByIdAndUserId(Long id, Long userId);

    @Query("select p from Penalty p join fetch p.application a join fetch a.campaign c where p.user.id = :userId")
    List<Penalty> findByUserIdWithCampaign(Long userId);

    @Query("select COALESCE(SUM(p.point), 0) from Penalty p where p.user.id = :userId")
    int getTotalScore(Long userId);
}
