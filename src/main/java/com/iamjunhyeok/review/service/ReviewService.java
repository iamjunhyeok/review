package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Review;
import com.iamjunhyeok.review.dto.ReviewCreateRequest;
import com.iamjunhyeok.review.dto.ReviewRejectRequest;
import com.iamjunhyeok.review.dto.ReviewUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.ApplicationRepository;
import com.iamjunhyeok.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ApplicationRepository applicationRepository;

    private final ReviewRepository reviewRepository;

    @Transactional
    public Review create(Long campaignId, Long applicationId, ReviewCreateRequest request) {
        Application application = applicationRepository.findByIdAndCampaignId(applicationId, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());

        Review review = Review.of(request.getReceiptUrl(), request.getBlogUrl(), request.getPostUrl());
        reviewRepository.save(review);

        application.registerReview(review);

        return review;
    }

    @Transactional
    public Review update(Long campaignId, Long applicationId, Long id, ReviewUpdateRequest request) {
        return reviewRepository.findByIdAndApplicationIdAndCampaignId(id, applicationId, campaignId)
                .orElseThrow(() -> ErrorCode.REVIEW_NOT_FOUND.build())
                .update(request.getReceiptUrl(), request.getBlogUrl(), request.getPostUrl());
    }

    @Transactional
    public Review modifyRequest(Long campaignId, Long applicationId, Long id, ReviewRejectRequest request) {
        return reviewRepository.findByIdAndApplicationIdAndCampaignId(id, applicationId, campaignId)
                .orElseThrow(() -> ErrorCode.REVIEW_NOT_FOUND.build())
                .modifyRequest(request.getDetails());
    }

    @Transactional
    public void reconfirmRequest(Long campaignId, Long applicationId, Long id) {
        reviewRepository.findByIdAndApplicationIdAndCampaignId(id, applicationId, campaignId)
                .orElseThrow(() -> ErrorCode.REVIEW_NOT_FOUND.build())
                .reconfirmRequest();
    }

    @Transactional
    public void confirm(Long campaignId, Long applicationId, Long id) {
        reviewRepository.findByIdAndApplicationIdAndCampaignId(id, applicationId, campaignId)
                .orElseThrow(() -> ErrorCode.REVIEW_NOT_FOUND.build())
                .confirm();
    }
}
