package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.Point;
import com.iamjunhyeok.review.projection.PointSearchProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomPointRepositoryImpl implements CustomPointRepository {

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    @Override
    public List<PointSearchProjection> search(Long id) {
        CriteriaBuilder<Point> cb = criteriaBuilderFactory.create(entityManager, Point.class, "p")
                .select("p.amount")
                .select("p.createdAt")
                .select("c.title")
                .innerJoinDefault("p.application", "a")
                .innerJoinDefault("a.campaign", "c")
                .where("p.user.id").eq(id);
        CriteriaBuilder<PointSearchProjection> criteriaBuilder = entityViewManager.applySetting(EntityViewSetting.create(PointSearchProjection.class), cb);
        return criteriaBuilder.getResultList();
    }
}
