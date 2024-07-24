package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Penalty;
import com.iamjunhyeok.review.projection.PenaltyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    Optional<Penalty> findByIdAndUserId(Long id, Long userId);

    @Query("select p from Penalty p join fetch p.application a join fetch a.campaign c where p.user.id = :userId")
    List<Penalty> findByUserIdWithCampaign(Long userId);

    List<PenaltyProjection> findAllByUserId(Long userId);
}
