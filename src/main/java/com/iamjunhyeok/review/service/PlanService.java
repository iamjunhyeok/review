package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.projection.PlanProjection;
import com.iamjunhyeok.review.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public List<PlanProjection> fetchAll() {
        return planRepository.fetchAll();
    }
}
