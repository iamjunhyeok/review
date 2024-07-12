package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.dto.PointSearchProjection;

import java.util.List;

public interface CustomPointRepository {

    List<PointSearchProjection> search(Long id);
}
