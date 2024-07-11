package com.iamjunhyeok.review.repository;


import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignType;
import com.iamjunhyeok.review.domain.Campaign;
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
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.iamjunhyeok.review.domain.QApplication.application;
import static com.iamjunhyeok.review.domain.QCampaign.campaign;

@RequiredArgsConstructor
public class CustomCampaignRepositoryImpl implements CustomCampaignRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    @Override
    public List<CampaignSearchProjection> search(String type, String category, String social, String filter, Pageable pageable, String swlat, String swlng, String nelat, String nelng) {
        NumberPath<Long> applicantsCount = Expressions.numberPath(Long.class, "applicantsCount");

//        List<CampaignType> types = Arrays.stream(type.split(","))
//                .map(String::toUpperCase)
//                .map(CampaignType::valueOf)
//                .toList();
//        List<CampaignCategory> categories = Arrays.stream(category.split(","))
//                .map(String::toUpperCase)
//                .map(CampaignCategory::valueOf)
//                .toList();
//        List<CampaignSocial> socials = Arrays.stream(social.split(","))
//                .map(String::toUpperCase)
//                .map(CampaignSocial::valueOf)
//                .toList();

        return jpaQueryFactory
                .select(Projections.fields(
                        CampaignSearchProjection.class,
                        campaign.id,
                        campaign.type,
                        campaign.social,
                        campaign.title,
                        campaign.capacity,
                        campaign.applicationEndDate,
                        ExpressionUtils.as(JPAExpressions.select(application.count())
                                .from(application)
                                .where(application.campaign.eq(campaign)), "applicantsCount"),

                        campaign.longitude,
                        campaign.latitude
                ))
                .from(campaign)
                .where(
                        eqType(type),
                        eqCategory(category),
                        betweenLatLng(swlat, swlng, nelat, nelng)
                )
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

    private BooleanExpression betweenLatLng(String swlat, String swlng, String nelat, String nelng) {
        if (StringUtils.isEmpty(swlat) || StringUtils.isEmpty(swlng) || StringUtils.isEmpty(nelat) || StringUtils.isEmpty(nelng)) return null;
        return campaign.latitude.between(swlat, nelat).and(campaign.longitude.between(swlng, nelng));
    }

    @Override
    public <T> Optional<T> fetchById(Long id, Class<T> type) {
        try {
            CriteriaBuilder<Campaign> cb = criteriaBuilderFactory.create(entityManager, Campaign.class)
                    .where("id").eq(id);
            CriteriaBuilder<T> builder = entityViewManager.applySetting(EntityViewSetting.create(type), cb);

            return Optional.of(builder.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
