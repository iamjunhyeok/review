package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, CustomReviewRepository {

    @Query("select r from Review r join r.application a join a.campaign c where r.id = :id and a.id = :applicationId and c.id = :campaignId")
    Optional<Review> findByIdAndApplicationIdAndCampaignId(Long id, Long applicationId, Long campaignId);
}
