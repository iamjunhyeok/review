package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.dto.ApplicantProjection;
import com.iamjunhyeok.review.dto.ApplicationProjection;
import com.iamjunhyeok.review.dto.UserCampaignApplicationProjection;

import java.util.List;

public interface CustomApplicationRepository {

    List<UserCampaignApplicationProjection> fetchAuthenticatedUserCampaignApplication(Long campaignId, Long applicationId);

    ApplicationProjection fetchOne(Long campaignId, Long applicationId);

    List<ApplicantProjection> fetchAllApplicants(Long campaignId);
}
