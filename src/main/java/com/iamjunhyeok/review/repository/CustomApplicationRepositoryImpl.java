package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.dto.UserCampaignApplicationProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RequiredArgsConstructor
public class CustomApplicationRepositoryImpl implements CustomApplicationRepository {

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    @Override
    public List<UserCampaignApplicationProjection> fetchAuthenticatedUserCampaignApplication(Long campaignId, Long applicationId) {
        CustomOAuth2User authentication = (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication();
        CriteriaBuilder<Application> campaignCriteriaBuilder = criteriaBuilderFactory.create(entityManager, Application.class, "a")
                .where("a.user.id").eq(authentication.getUserId())
                .where("a.campaign.id").eq(campaignId)
                .where("a.id").eq(applicationId);
        return entityViewManager.applySetting(EntityViewSetting.create(UserCampaignApplicationProjection.class), campaignCriteriaBuilder).getResultList();
    }
}
