package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.projection.ApplicationViewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>, CustomApplicationRepository {

    boolean existsByUserIdAndCampaignId(Long userId, Long campaignId);

    Optional<Application> findByIdAndCampaignId(Long id, Long campaignId);

    Optional<ApplicationViewProjection> findApplicationByIdAndCampaignId(Long id, Long campaignId);

    Optional<Application> findByUserIdAndCampaignId(Long userId, Long campaignId);

    @Query("select a from Application a join fetch a.user where a.campaign.id = :campaignId")
    List<Application> findByCampaignIdWithUsers(Long campaignId);

    Optional<Application> findByIdAndUserId(Long id, Long userId);

    List<Application> findAllByCampaignIdAndStatus(Long campaignId, ApplicationStatus status);
}
