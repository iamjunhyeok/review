package com.iamjunhyeok.review.repository;


import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.constant.CampaignSort;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.domain.QFavourite;
import com.iamjunhyeok.review.projection.CampaignImageProjection;
import com.iamjunhyeok.review.projection.CampaignLinkProjection;
import com.iamjunhyeok.review.projection.CampaignMissionProjection;
import com.iamjunhyeok.review.projection.CampaignOptionProjection;
import com.iamjunhyeok.review.projection.CampaignProjection;
import com.iamjunhyeok.review.projection.CodeProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.blazebit.persistence.querydsl.JPQLNextExpressions.count;
import static com.iamjunhyeok.review.domain.QApplication.application;
import static com.iamjunhyeok.review.domain.QCampaign.campaign;
import static com.iamjunhyeok.review.domain.QCampaignImage.campaignImage;
import static com.iamjunhyeok.review.domain.QCampaignLink.campaignLink;
import static com.iamjunhyeok.review.domain.QCampaignMission.campaignMission;
import static com.iamjunhyeok.review.domain.QCampaignOption.campaignOption;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.group.GroupBy.set;

@RequiredArgsConstructor
public class CustomCampaignRepositoryImpl implements CustomCampaignRepository {

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    private final JPAQueryFactory qf;

    private final RegionMappingRepository regionMappingRepository;

    @Override
    public List<CampaignProjection> fetchAll(Long typeCodeId, Long[] categoryCodeIds, Long[] socialCodeIds, Long[] optionCodeIds, Long regionCodeId, Pageable pageable, String swlat, String swlng, String nelat, String nelng) {
        String[] array = regionMappingRepository.findTrimmedCodesByRegionGroupId(regionCodeId)
                .stream().map(aLong -> String.valueOf(aLong)).toList().toArray(new String[0]);

        Long userId = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
            userId = principal.getUserId();
        }

        QFavourite favourite = QFavourite.favourite;
        List<CampaignProjection> fetch = qf.from(campaign)
                .innerJoin(campaign.images, campaignImage)
                .leftJoin(campaign.favourites, favourite)
                .on(favourite.user.id.eq(userId))
                .where(
                        eq(campaign.typeCode.id, typeCodeId),
                        in(campaign.categoryCode.id, categoryCodeIds),
                        in(campaign.socialCode.id, socialCodeIds),
                        in(campaign.administrativeDistrictCode, array),
                        existsOption(optionCodeIds)
                )
                .orderBy(order(CampaignSort.DEADLINE))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .transform(
                        groupBy(campaign.id).list(
                                Projections.fields(
                                        CampaignProjection.class,
                                        campaign.id,
                                        Projections.fields(
                                                CodeProjection.class,
                                                campaign.typeCode.id,
                                                campaign.typeCode.code,
                                                campaign.typeCode.value
                                        ).as("typeCode"),
                                        Projections.fields(
                                                CodeProjection.class,
                                                campaign.categoryCode.id,
                                                campaign.categoryCode.code,
                                                campaign.categoryCode.value
                                        ).as("categoryCode"),
                                        Projections.fields(
                                                CodeProjection.class,
                                                campaign.socialCode.id,
                                                campaign.socialCode.code,
                                                campaign.socialCode.value
                                        ).as("socialCode"),
                                        campaign.title,
                                        campaign.capacity,
                                        campaign.applicationEndDate,
                                        campaign.announcementDate,
                                        campaign.offeringSummary,
                                        ExpressionUtils.as(
                                                JPAExpressions.select(count())
                                                        .from(application)
                                                        .where(
                                                                application.campaign.id.eq(campaign.id)
                                                                .and(application.status.ne(ApplicationStatus.CANCELLED))
                                                        )
                                                , "applicantsCount"
                                        ),
                                        Expressions.numberTemplate(
                                                Integer.class,
                                                "DATEDIFF({0}, CURDATE())",
                                                campaign.applicationEndDate
                                        ).as("dDay"),
                                        campaign.longitude,
                                        campaign.latitude,
                                        list(
                                                Projections.fields(
                                                        CampaignImageProjection.class,
                                                        campaignImage.id,
                                                        campaignImage.name
                                                )
                                        ).as("images"),
                                        favourite.isNotNull().as("isFavourite")
                                )
                        )
                );
        return fetch;
    }

    @Override
    public CampaignProjection fetchOne(Long id) {
        return qf.from(campaign)
                .innerJoin(campaign.images, campaignImage)
                .where(campaign.id.eq(id))
                .transform(
                        groupBy(campaign.id).list(
                                Projections.fields(
                                        CampaignProjection.class,
                                        campaign.id,
                                        Projections.fields(
                                                CodeProjection.class,
                                                campaign.typeCode.id,
                                                campaign.typeCode.code,
                                                campaign.typeCode.value
                                        ).as("typeCode"),
                                        Projections.fields(
                                                CodeProjection.class,
                                                campaign.categoryCode.id,
                                                campaign.categoryCode.code,
                                                campaign.categoryCode.value
                                        ).as("categoryCode"),
                                        Projections.fields(
                                                CodeProjection.class,
                                                campaign.socialCode.id,
                                                campaign.socialCode.code,
                                                campaign.socialCode.value
                                        ).as("socialCode"),
                                        campaign.title,
                                        campaign.announcementDate,
                                        campaign.reviewStartDate,
                                        campaign.reviewEndDate,
                                        list(
                                                Projections.fields(
                                                        CampaignImageProjection.class,
                                                        campaignImage.id,
                                                        campaignImage.name
                                                )
                                        ).as("images")
                                )
                        )
                ).get(0);
    }

    @Override
    public CampaignProjection fetchOneDetail(Long id) {
        List<CampaignProjection> transform = qf.from(campaign)
                .innerJoin(campaign.images, campaignImage)
                .leftJoin(campaign.links, campaignLink)
                .leftJoin(campaign.missions, campaignMission)
                .leftJoin(campaign.options, campaignOption)
                .where(campaign.id.eq(id))
                .transform(
                        groupBy(campaign.id).list(
                                Projections.fields(
                                        CampaignProjection.class,
                                        campaign.id,
                                        Projections.fields(
                                                CodeProjection.class,
                                                campaign.typeCode.id,
                                                campaign.typeCode.code,
                                                campaign.typeCode.value
                                        ).as("typeCode"),
                                        Projections.fields(
                                                CodeProjection.class,
                                                campaign.categoryCode.id,
                                                campaign.categoryCode.code,
                                                campaign.categoryCode.value
                                        ).as("categoryCode"),
                                        Projections.fields(
                                                CodeProjection.class,
                                                campaign.socialCode.id,
                                                campaign.socialCode.code,
                                                campaign.socialCode.value
                                        ).as("socialCode"),
                                        campaign.title,
                                        campaign.capacity,
                                        campaign.applicationStartDate,
                                        campaign.applicationEndDate,
                                        campaign.announcementDate,
                                        campaign.reviewStartDate,
                                        campaign.reviewEndDate,
                                        campaign.offering,
                                        campaign.offeringSummary,
                                        campaign.keyword,
                                        campaign.hashtag,
                                        campaign.guide,
                                        campaign.information,
                                        campaign.status,
                                        campaign.point,
                                        campaign.address,
                                        campaign.rest,
                                        campaign.postalCode,
                                        campaign.longitude,
                                        campaign.latitude,
                                        set(
                                                Projections.fields(
                                                        CampaignLinkProjection.class,
                                                        campaignLink.id,
                                                        campaignLink.url
                                                )
                                        ).as("links"),
                                        set(
                                                Projections.fields(
                                                        CampaignImageProjection.class,
                                                        campaignImage.id,
                                                        campaignImage.name
                                                )
                                        ).as("images"),
                                        set(
                                                Projections.fields(
                                                        CampaignMissionProjection.class,
                                                        campaignMission.id,
                                                        campaignMission.value
                                                )
                                        ).as("missions"),
                                        set(
                                                Projections.fields(
                                                        CampaignOptionProjection.class,
                                                        campaignOption.id
                                                )
                                        ).as("options")
                                )
                        )
                );
        CampaignProjection campaignProjection = transform.get(0);
        return campaignProjection;
    }

    private OrderSpecifier<?> order(CampaignSort campaignSort) {
        return switch (campaignSort) {
            case NEW -> new OrderSpecifier<>(Order.DESC, campaign.createdAt);
            case POPULAR -> new OrderSpecifier<>(Order.DESC, JPAExpressions.select(count())
                    .from(application)
                    .where(
                            application.campaign.id.eq(campaign.id)
                                    .and(application.status.ne(ApplicationStatus.CANCELLED))
                    ));
            case DEADLINE -> new OrderSpecifier<>(Order.ASC, Expressions.numberTemplate(
                    Integer.class,
                    "DATEDIFF({0}, CURDATE())",
                    campaign.applicationEndDate
            ));
        };
    }

    private <T> Predicate eq(SimpleExpression<T> path, T value) {
        if (value == null) {
            return null;
        }
        return path.eq(value);
    }

    public <T> Predicate in(SimpleExpression<T> path, T[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        return path.in(values);
    }

    private static BooleanExpression existsOption(Long[] optionCodeIds) {
        if (optionCodeIds == null || optionCodeIds.length == 0) {
            return null;
        }
        return JPAExpressions.selectOne()
                .from(campaignOption)
                .where(
                        campaignOption.id.eq(campaign.id)
                        .and(campaignOption.code.id.in(optionCodeIds))
                )
                .exists();
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

    @Override
    public List<UserCampaignSearchProjection> fetchAuthenticatedUserCampaigns(String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();

        List<ApplicationStatus> statuses = Arrays.stream(status.toUpperCase().split(","))
                .map(ApplicationStatus::valueOf)
                .toList();

        CriteriaBuilder<Campaign> campaignCriteriaBuilder = criteriaBuilderFactory.create(entityManager, Campaign.class)
                .innerJoinDefault("applications", "a")
                .where("a.user.id").eq(principal.getUserId())
                .where("a.status").in(statuses)
                .where("a.deleted").eqLiteral(false);
        return entityViewManager.applySetting(EntityViewSetting.create(UserCampaignSearchProjection.class), campaignCriteriaBuilder).getResultList();
    }
}
