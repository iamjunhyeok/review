package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.Withdrawal;
import com.iamjunhyeok.review.projection.WithdrawalWithUserProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomWithdrawalRepositoryImpl implements CustomWithdrawalRepository {
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    @Override
    public List<WithdrawalWithUserProjection> fetchAllWithdrawalHistory() {
        CriteriaBuilder<Withdrawal> cbf = criteriaBuilderFactory.create(entityManager, Withdrawal.class);
        return entityViewManager.applySetting(EntityViewSetting.create(WithdrawalWithUserProjection.class), cbf).getResultList();
    }
}
