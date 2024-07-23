package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.dto.UserCampaignApplicationProjection;

import java.util.List;

public interface CustomApplicationRepository {

    List<UserCampaignApplicationProjection> fetchAuthenticatedUserCampaignApplication(Long campaignId, Long applicationId);
}
