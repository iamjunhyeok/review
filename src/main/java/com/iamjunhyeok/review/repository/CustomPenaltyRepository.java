package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.PenaltyProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPenaltyRepository {

    List<PenaltyProjection> fetchAllPenaltyHistoryByUserId(Long userId, Pageable pageable);
}
