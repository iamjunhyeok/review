package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.CodeProjection;
import com.iamjunhyeok.review.projection.InquiryAnswerProjection;
import com.iamjunhyeok.review.projection.InquiryProjection;
import com.iamjunhyeok.review.projection.UserProjection;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.iamjunhyeok.review.domain.QInquiry.inquiry;

@RequiredArgsConstructor
public class CustomInquiryRepositoryImpl implements CustomInquiryRepository {

    private final JPAQueryFactory qf;

    @Override
    public List<InquiryProjection> fetchAllInquiriesByUserId(Long userId, Pageable pageable) {
        List<InquiryProjection> fetch = qf.select(
                        Projections.fields(InquiryProjection.class,
                                inquiry.id,
                                Projections.fields(
                                        CodeProjection.class,
                                        inquiry.categoryCode.id,
                                        inquiry.categoryCode.code,
                                        inquiry.categoryCode.value
                                ).as("categoryCode"),
                                inquiry.title,
                                inquiry.content,
                                inquiry.createdAt,
                                inquiry.updatedAt
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
    public List<InquiryProjection> fetchAll(Long categoryCodeId, Pageable pageable) {
        return qf.select(
                        Projections.fields(InquiryProjection.class,
                                inquiry.id,
                                Projections.fields(
                                        CodeProjection.class,
                                        inquiry.categoryCode.id,
                                        inquiry.categoryCode.code,
                                        inquiry.categoryCode.value
                                ).as("categoryCode"),
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
                .where(eq(inquiry.categoryCode.id, categoryCodeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public InquiryProjection fetchOne(Long id) {
        return qf.select(
                        Projections.fields(InquiryProjection.class,
                                inquiry.id,
                                Projections.fields(
                                        CodeProjection.class,
                                        inquiry.categoryCode.id,
                                        inquiry.categoryCode.code,
                                        inquiry.categoryCode.value
                                ).as("categoryCode"),
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
                                ).as("answer")
                        )
                ).from(inquiry)
                .innerJoin(inquiry.user)
                .leftJoin(inquiry.inquiryAnswer)
                .fetchOne();
    }

    private <T> Predicate eq(SimpleExpression<T> path, T value) {
        if (value == null) {
            return null;
        }
        return path.eq(value);
    }
}
