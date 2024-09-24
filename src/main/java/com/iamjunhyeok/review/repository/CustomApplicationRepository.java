package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.ApplicantProjection;
import com.iamjunhyeok.review.projection.ApplicationProjection;
import com.iamjunhyeok.review.projection.ApplicationSearchProjection;

import java.util.List;

public interface CustomApplicationRepository {

    ApplicationProjection fetchOne(Long campaignId, Long applicationId);

    List<ApplicantProjection> fetchAllApplicants(Long campaignId);

    List<ApplicationSearchProjection> fetchAllApplications(String status, Long userId);
}
