package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.UserSearchProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    @Override
    public List<UserSearchProjection> search() {
        CriteriaBuilder<User> userCriteriaBuilder = criteriaBuilderFactory.create(entityManager, User.class);
        CriteriaBuilder<UserSearchProjection> userSearchProjectionCriteriaBuilder = entityViewManager.applySetting(EntityViewSetting.create(UserSearchProjection.class), userCriteriaBuilder);
        return userSearchProjectionCriteriaBuilder.getResultList();
    }
}
