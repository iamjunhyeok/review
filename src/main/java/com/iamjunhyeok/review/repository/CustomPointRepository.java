package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.PointProjection;
import com.iamjunhyeok.review.projection.PointSearchProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPointRepository {

    List<PointSearchProjection> search(Long id);

    List<PointProjection> fetchAllPointsHistoryByUserId(Long userId, Pageable pageable);
}
