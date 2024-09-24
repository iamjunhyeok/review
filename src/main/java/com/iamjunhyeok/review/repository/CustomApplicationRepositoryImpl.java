package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.projection.ApplicantProjection;
import com.iamjunhyeok.review.projection.ApplicationProjection;
import com.iamjunhyeok.review.projection.ApplicationSearchProjection;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.iamjunhyeok.review.domain.QApplication.application;
import static com.iamjunhyeok.review.domain.QPenalty.penalty;

@RequiredArgsConstructor
public class CustomApplicationRepositoryImpl implements CustomApplicationRepository {

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    private final JPAQueryFactory qf;

    @Override
    public ApplicationProjection fetchOne(Long campaignId, Long applicationId) {
        return qf.select(
                        Projections.fields(
                                ApplicationProjection.class,
                                application.id,
                                application.name,
                                application.phoneNumber,
                                application.address,
                                application.rest,
                                application.postalCode,
                                application.status
                        )
                )
                .from(application)
                .where(
                        application.campaign.id.eq(campaignId)
                                .and(application.id.eq(applicationId))
                )
                .fetchOne();
    }

    @Override
    public List<ApplicantProjection> fetchAllApplicants(Long campaignId) {
        return qf.select(
                        Projections.fields(
                                ApplicantProjection.class,
                                application.id,
                                application.name,
                                application.phoneNumber,
                                application.address,
                                application.rest,
                                application.postalCode,
                                application.status,
                                ExpressionUtils.as(
                                        JPAExpressions.select(penalty.point.sum().coalesce(0))
                                                .from(penalty)
                                                .where(penalty.user.id.eq(application.user.id))
                                                .where(penalty.createdAt.goe(LocalDateTime.now().minusMonths(3))), "penaltyPoints")
                        )
                )
                .from(application)
                .innerJoin(application.campaign)
                .innerJoin(application.user)
                .where(
                        application.campaign.id.eq(campaignId)
                                .and(application.status.ne(ApplicationStatus.CANCELLED))
                )
                .fetch();
    }

    @Override
    public List<ApplicationSearchProjection> fetchAllApplications(String status, Long userId) {
        List<ApplicationStatus> statuses = Arrays.stream(status.toUpperCase().split(","))
                .map(ApplicationStatus::valueOf)
                .toList();

        CriteriaBuilder<Application> campaignCriteriaBuilder = criteriaBuilderFactory.create(entityManager, Application.class, "a")
                .innerJoinDefault("campaign", "c")
                .where("a.user.id").eq(userId)
                .where("a.status").in(statuses)
                .where("a.deleted").eqLiteral(false);
        return entityViewManager.applySetting(EntityViewSetting.create(ApplicationSearchProjection.class), campaignCriteriaBuilder).getResultList();
    }
}
