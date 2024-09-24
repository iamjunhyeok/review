package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.ReviewProjection;

import java.util.List;

public interface CustomReviewRepository {
    List<ReviewProjection> fetchAll(Long campaignId, Long applicationId);

}
