package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.domain.QInquiry;
import com.iamjunhyeok.review.projection.InquiryAnswerProjection;
import com.iamjunhyeok.review.projection.InquiryProjection;
import com.iamjunhyeok.review.projection.InquiryWithAnswerAndUserProjection;
import com.iamjunhyeok.review.projection.UserProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RequiredArgsConstructor
public class CustomInquiryRepositoryImpl implements CustomInquiryRepository {

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    private final JPAQueryFactory qf;

    @Override
    public List<InquiryWithAnswerAndUserProjection> fetchAllInquiriesForAuthenticatedUser(String category) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        CriteriaBuilder<Inquiry> cb = criteriaBuilderFactory.create(entityManager, Inquiry.class, "i");
        cb.where("i.user.id").eq(principal.getUserId());
        if (Strings.isNotBlank(category)) cb.where("i.category").eq(category);
        return entityViewManager.applySetting(EntityViewSetting.create(InquiryWithAnswerAndUserProjection.class), cb).getResultList();
    }

    @Override
    public List<InquiryWithAnswerAndUserProjection> fetchAllInquiries(String category) {
        CriteriaBuilder<Inquiry> cb = criteriaBuilderFactory.create(entityManager, Inquiry.class, "i");
        if (Strings.isNotBlank(category)) cb.where("i.category").eq(category);
        return entityViewManager.applySetting(EntityViewSetting.create(InquiryWithAnswerAndUserProjection.class), cb).getResultList();
    }

    @Override
    public List<InquiryProjection> fetchAllInquiriesByUserId(Long userId, Pageable pageable) {
        QInquiry inquiry = QInquiry.inquiry;
        List<InquiryProjection> fetch = qf.select(
                        Projections.fields(InquiryProjection.class,
                                inquiry.id,
                                inquiry.category,
                                inquiry.title,
                                inquiry.content,
                                inquiry.createdAt,
                                inquiry.updatedAt,
                                Projections.fields(
                                        InquiryAnswerProjection.class,
                                        inquiry.inquiryAnswer.id,
                                        inquiry.inquiryAnswer.title,
                                        inquiry.inquiryAnswer.content
                                ).as("inquiryAnswer")
                        )
                ).from(inquiry)
                .innerJoin(inquiry.user)
                .leftJoin(inquiry.inquiryAnswer)
                .where(inquiry.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return fetch;
    }

    @Override
    public List<InquiryProjection> fetchAllInquiries(Pageable pageable) {
        QInquiry inquiry = QInquiry.inquiry;
        List<InquiryProjection> fetch = qf.select(
                        Projections.fields(InquiryProjection.class,
                                inquiry.id,
                                inquiry.category,
                                inquiry.title,
                                inquiry.content,
                                inquiry.createdAt,
                                inquiry.updatedAt,
                                Projections.fields(
                                        UserProjection.class,
                                        inquiry.user.id,
                                        inquiry.user.email,
                                        inquiry.user.nickname,
                                        inquiry.user.profileImageName
                                ).as("user"),
                                Projections.fields(
                                        InquiryAnswerProjection.class,
                                        inquiry.inquiryAnswer.id,
                                        inquiry.inquiryAnswer.title,
                                        inquiry.inquiryAnswer.content
                                ).as("inquiryAnswer")
                        )
                ).from(inquiry)
                .innerJoin(inquiry.user)
                .leftJoin(inquiry.inquiryAnswer)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return fetch;
    }
}
