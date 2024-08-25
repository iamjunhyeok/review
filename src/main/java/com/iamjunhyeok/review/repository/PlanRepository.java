package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long>, CustomPlanRepository {
}
