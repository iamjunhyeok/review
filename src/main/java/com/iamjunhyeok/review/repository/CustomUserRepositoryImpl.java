package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.UserCampaignApplicationProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import com.iamjunhyeok.review.projection.UserSearchProjection;
import com.iamjunhyeok.review.projection.UserSummaryProjection;
import com.iamjunhyeok.review.projection.UserView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final CriteriaBuilderFactory cbf;

    private final EntityManager em;

    private final EntityViewManager evm;

    @Override
    public List<UserSearchProjection> search() {
        CriteriaBuilder<User> userCriteriaBuilder = cbf.create(em, User.class);
        return evm.applySetting(EntityViewSetting.create(UserSearchProjection.class), userCriteriaBuilder).getResultList();
    }

    @Override
    public Optional<UserView> fetchById(Long id) {
        try {
            CriteriaBuilder<User> userCriteriaBuilder = cbf.create(em, User.class)
                    .where("id").eq(id);
            CriteriaBuilder<UserView> userViewProjectionCriteriaBuilder = evm.applySetting(EntityViewSetting.create(UserView.class), userCriteriaBuilder);
            return Optional.of(userViewProjectionCriteriaBuilder.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserCampaignSearchProjection> fetchAllByUserIdAndApplicationStatus(Long userId, ApplicationStatus status) {
        CriteriaBuilder<Campaign> campaignCriteriaBuilder = cbf.create(em, Campaign.class)
                .innerJoinDefault("applications", "a")
                .where("a.user.id").eq(userId)
                .where("a.status").eq(status);
        CriteriaBuilder<UserCampaignSearchProjection> myCampaignSearchProjectionCriteriaBuilder = evm.applySetting(EntityViewSetting.create(UserCampaignSearchProjection.class), campaignCriteriaBuilder);
        return myCampaignSearchProjectionCriteriaBuilder.getResultList();
    }

    @Override
    public UserCampaignApplicationProjection fetchUserCampaignApplication(Long userId, Long campaignId, Long applicationId) {
        try {
            CriteriaBuilder<Application> applicationCriteriaBuilder = cbf.create(em, Application.class, "a")
                    .where("a.user.id").eq(userId)
                    .where("a.campaign.id").eq(campaignId)
                    .where("a.id").eq(applicationId);
            CriteriaBuilder<UserCampaignApplicationProjection> userCampaignApplicationProjectionCriteriaBuilder = evm.applySetting(EntityViewSetting.create(UserCampaignApplicationProjection.class), applicationCriteriaBuilder);
            return userCampaignApplicationProjectionCriteriaBuilder.getSingleResult();
        } catch (NoResultException e) {
            throw ErrorCode.APPLICATION_NOT_FOUND.build();
        }
    }

    @Override
    public UserSummaryProjection fetchUserSummary(Long userId) {
        CriteriaBuilder<User> cb = cbf.create(em, User.class)
                .where("id").eq(userId);
        return evm.applySetting(EntityViewSetting.create(UserSummaryProjection.class), cb).getSingleResult();
    }
}
