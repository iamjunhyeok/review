package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Review;
import com.iamjunhyeok.review.dto.ReviewCreateRequest;
import com.iamjunhyeok.review.dto.ReviewDto;
import com.iamjunhyeok.review.dto.ReviewRejectRequest;
import com.iamjunhyeok.review.dto.ReviewUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.ApplicationRepository;
import com.iamjunhyeok.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ApplicationRepository applicationRepository;

    private final ReviewRepository reviewRepository;

    @Transactional
    public List<Review> create(Long campaignId, Long applicationId, ReviewCreateRequest request) {
        Application application = applicationRepository.findByIdAndCampaignId(applicationId, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());

        List<Review> list = request.getReviews()
                .stream().map(dto -> Review.of(dto.getType(), dto.getUrl()))
                .toList();

        application.registerReviews(list);

        return list;
    }

    @Transactional
    public List<Review> update(Long campaignId, Long applicationId, ReviewUpdateRequest request) {
        Application application = applicationRepository.findByIdAndCampaignId(applicationId, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());

        reviewRepository.deleteAllByIdInBatch(request.getDeleteReviewIds());

        List<Long> existingIds = request.getReviews()
                .stream()
                .filter(dto -> dto.getId() != null)
                .map(ReviewDto::getId)
                .toList();

        Map<Long, Review> existingMap = reviewRepository.findAllById(existingIds)
                .stream()
                .collect(Collectors.toMap(Review::getId, review -> review));

        List<Review> returnList = new ArrayList<>();
        request.getReviews().stream().forEach(dto -> {
            Review review;
            if (existingMap.containsKey(dto.getId())) {
                review = existingMap.get(dto.getId());
                review.setUrl(dto.getUrl());
            } else {
                review = Review.of(dto.getType(), dto.getUrl());
                application.registerReview(review);
            }
            returnList.add(review);
        });

        return returnList;
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
