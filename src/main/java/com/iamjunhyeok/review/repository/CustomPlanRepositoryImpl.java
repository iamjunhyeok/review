package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.Plan;
import com.iamjunhyeok.review.projection.PlanProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomPlanRepositoryImpl implements CustomPlanRepository {

    private final CriteriaBuilderFactory cbf;

    private final EntityManager em;

    private final EntityViewManager evm;

    @Override
    public List<PlanProjection> fetchAll() {
        CriteriaBuilder<Plan> cb = cbf.create(em, Plan.class);
        return evm.applySetting(EntityViewSetting.create(PlanProjection.class), cb).getResultList();
    }

}
