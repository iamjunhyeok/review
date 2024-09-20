package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.ApplicantProjection;
import com.iamjunhyeok.review.projection.ApplicationView;
import com.iamjunhyeok.review.projection.ApplicationSearchProjection;
import com.iamjunhyeok.review.projection.UserCampaignApplicationProjection;

import java.util.List;

public interface CustomApplicationRepository {

    List<UserCampaignApplicationProjection> fetchAuthenticatedUserCampaignApplication(Long campaignId, Long applicationId);

    ApplicationView fetchOne(Long campaignId, Long applicationId);

    List<ApplicantProjection> fetchAllApplicants(Long campaignId);

    List<ApplicationSearchProjection> fetchAllApplications(String status, Long userId);
}
