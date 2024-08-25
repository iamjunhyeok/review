package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.PlanProjection;

import java.util.List;

public interface CustomPlanRepository {
    List<PlanProjection> fetchAll();
}
