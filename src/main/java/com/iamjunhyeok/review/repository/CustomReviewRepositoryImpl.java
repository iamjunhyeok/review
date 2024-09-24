package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.Review;
import com.iamjunhyeok.review.projection.ReviewProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository {

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    @Override
    public List<ReviewProjection> fetchAll(Long campaignId, Long applicationId) {
        CriteriaBuilder<Review> cb = criteriaBuilderFactory.create(entityManager, Review.class, "r")
                .innerJoinDefault("r.application", "a")
                .innerJoinDefault("a.campaign", "c");
        return entityViewManager.applySetting(EntityViewSetting.create(ReviewProjection.class), cb).getResultList();
    }
}
