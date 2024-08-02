package com.iamjunhyeok.review.repository;


import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignType;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.projection.CampaignSearchProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomCampaignRepositoryImpl implements CustomCampaignRepository {

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    @Override
    public List<CampaignSearchProjection> fetchAll(String type, String categories, String socials, String options, Pageable pageable, String swlat, String swlng, String nelat, String nelng) {
        CriteriaBuilder<Campaign> cb = criteriaBuilderFactory.create(entityManager, Campaign.class, "c")
                .innerJoinDefault("c.images", "i")
                .innerJoinDefault("c.options", "o")
                .innerJoinDefault("o.code", "co");
        if (Strings.isNotBlank(type)) {
            cb.where("c.type").eq(CampaignType.valueOf(type.toUpperCase()));
        }
        if (Strings.isNotBlank(categories)) {
            cb.where("c.category").in(
                    Arrays.stream(categories.toUpperCase().split(","))
                            .map(CampaignCategory::valueOf)
                            .toList()
            );
        }
        if (Strings.isNotBlank(socials)) {
            cb.where("c.social").in(
                    Arrays.stream(socials.toUpperCase().split(","))
                            .map(CampaignSocial::valueOf)
                            .toList()
            );
        }
        if (Strings.isNotBlank(options)) {
            cb.where("co.code").in(
                    Arrays.stream(options.toUpperCase().split(","))
                            .toList()
            );
        }
        for (Sort.Order order : pageable.getSort()) {
            if (order.getProperty().equals("newest")) {
                cb.orderByAsc("c.createdAt");
            }
        }
        cb.page(pageable.getOffset(), pageable.getPageSize());
        CriteriaBuilder<CampaignSearchProjection> criteriaBuilder = entityViewManager.applySetting(EntityViewSetting.create(CampaignSearchProjection.class), cb);
        return criteriaBuilder.getResultList();
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
    public List<UserCampaignSearchProjection> fetchAuthenticatedUserCampaigns(ApplicationStatus status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        CriteriaBuilder<Campaign> campaignCriteriaBuilder = criteriaBuilderFactory.create(entityManager, Campaign.class)
                .innerJoinDefault("applications", "a")
                .where("a.user.id").eq(principal.getUserId())
                .where("a.status").eq(status);
        return entityViewManager.applySetting(EntityViewSetting.create(UserCampaignSearchProjection.class), campaignCriteriaBuilder).getResultList();
    }
}
