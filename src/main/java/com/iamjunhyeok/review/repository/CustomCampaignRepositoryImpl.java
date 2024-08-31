package com.iamjunhyeok.review.repository;


import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.persistence.view.Sorters;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignSort;
import com.iamjunhyeok.review.constant.CampaignStatus;
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

import java.time.LocalDate;
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
                .leftJoinDefault("c.options", "o")
                .leftJoinDefault("o.code", "co");
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

        cb.page(pageable.getOffset(), pageable.getPageSize());

        EntityViewSetting<CampaignSearchProjection, CriteriaBuilder<CampaignSearchProjection>> setting = EntityViewSetting.create(CampaignSearchProjection.class);

        for (Sort.Order order : pageable.getSort()) {
            if (order.getProperty().equalsIgnoreCase(CampaignSort.NEW.name())) {
                cb.orderByDesc("c.createdAt");
            }
            if (order.getProperty().equalsIgnoreCase(CampaignSort.POPULAR.name())) {
                setting.addAttributeSorter("applicantsCount", Sorters.descending());
            }
            if (order.getProperty().equalsIgnoreCase(CampaignSort.DEADLINE.name())) {
                setting.addAttributeSorter("dday", Sorters.ascending());
            }
        }
        cb.where("c.applicationEndDate").gtLiteral(LocalDate.now());


        CriteriaBuilder<CampaignSearchProjection> criteriaBuilder = entityViewManager.applySetting(setting, cb);
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

    @Override
    public List<CampaignSearchProjection> fetchAll(String type, String categories, String socials, String options, String status, Pageable pageable) {
        CriteriaBuilder<Campaign> cb = criteriaBuilderFactory.create(entityManager, Campaign.class, "c")
                .innerJoinDefault("c.images", "i")
                .leftJoinDefault("c.options", "o")
                .leftJoinDefault("o.code", "co");
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
        if (Strings.isNotBlank(status)) {
            cb.where("c.status").in(
                    Arrays.stream(status.toUpperCase().split(","))
                            .map(CampaignStatus::valueOf)
                            .toList()
            );
        }
        for (Sort.Order order : pageable.getSort()) {
            if (order.getProperty().equals("newest")) {
                cb.orderByAsc("c.createdAt");
            }
        }
        cb.page(pageable.getOffset(), pageable.getPageSize());
        return entityViewManager.applySetting(EntityViewSetting.create(CampaignSearchProjection.class), cb).getResultList();
    }
}
