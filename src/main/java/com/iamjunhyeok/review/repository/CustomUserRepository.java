package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.projection.UserCampaignApplicationProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import com.iamjunhyeok.review.projection.UserSearchProjection;
import com.iamjunhyeok.review.projection.UserViewProjection;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository {

    List<UserSearchProjection> search();

    Optional<UserViewProjection> fetchById(Long id);

    List<UserCampaignSearchProjection> fetchAllByUserIdAndApplicationStatus(Long userId, ApplicationStatus status);

    UserCampaignApplicationProjection fetchUserCampaignApplication(Long userId, Long campaignId, Long applicationId);
}
