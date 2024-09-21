package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.projection.UserCampaignApplicationProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import com.iamjunhyeok.review.projection.UserProjection;
import com.iamjunhyeok.review.projection.UserSearchProjection;
import com.iamjunhyeok.review.projection.UserSummaryProjection;
import com.iamjunhyeok.review.projection.UserView;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository {

    List<UserSearchProjection> search();

    Optional<UserView> fetchById(Long id);

    List<UserCampaignSearchProjection> fetchAllByUserIdAndApplicationStatus(Long userId, ApplicationStatus status);

    UserCampaignApplicationProjection fetchUserCampaignApplication(Long userId, Long campaignId, Long applicationId);

    UserSummaryProjection fetchUserSummary(Long userId);

    List<UserProjection> fetchAll(Long id, String email, String nickname);

    UserProjection fetchOne(Long id);
}
