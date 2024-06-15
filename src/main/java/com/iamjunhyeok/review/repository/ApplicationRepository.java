package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByUserIdAndCampaignId(Long userId, Long campaignId);

    Optional<Application> findByIdAndCampaignId(Long id, Long campaignId);

    Optional<Application> findByUserIdAndCampaignId(Long userId, Long campaignId);
}
