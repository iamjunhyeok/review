package com.iamjunhyeok.review.repository;


import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignType;
import com.iamjunhyeok.review.dto.CampaignSearchProjection;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.iamjunhyeok.review.domain.QApplication.application;
import static com.iamjunhyeok.review.domain.QCampaign.campaign;

@RequiredArgsConstructor
public class CustomCampaignRepositoryImpl implements CustomCampaignRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CampaignSearchProjection> search(String type, String category, String filter, Pageable pageable) {
        NumberPath<Long> applicantsCount = Expressions.numberPath(Long.class, "applicantsCount");

        return jpaQueryFactory
                .select(Projections.fields(
                        CampaignSearchProjection.class,
                        campaign.type,
                        campaign.social,
                        campaign.title,
                        campaign.capacity,
                        campaign.applicationEndDate,
                        ExpressionUtils.as(JPAExpressions.select(application.count())
                                .from(application)
                                .where(application.campaign.eq(campaign)), "applicantsCount")
                ))
                .from(campaign)
                .where(eqType(type), eqCategory(category))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable.getSort(), applicantsCount))
                .fetch();
    }

    private static OrderSpecifier[] getOrderSpecifiers(Sort sort, NumberPath<Long> applicantsCount) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        for (Sort.Order order : sort) {
            if (order.getProperty().equals("newest")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, campaign.createdAt));
            }
            if (order.getProperty().equals("popular")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, applicantsCount));
            }
            if (order.getProperty().equals("deadline")) {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, campaign.applicationEndDate));
            }
            if (order.getProperty().equals("probability")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, campaign.capacity));
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, applicantsCount));
            }
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    private BooleanExpression eqType(String type) {
        if (StringUtils.isEmpty(type)) return null;
        return campaign.type.eq(CampaignType.valueOf(type.toUpperCase()));
    }

    private BooleanExpression eqCategory(String category) {
        if (StringUtils.isEmpty(category)) return null;
        return campaign.category.eq(CampaignCategory.valueOf(category.toUpperCase()));
    }
}
