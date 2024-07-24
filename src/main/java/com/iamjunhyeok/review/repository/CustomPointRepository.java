package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.PointSearchProjection;

import java.util.List;

public interface CustomPointRepository {

    List<PointSearchProjection> search(Long id);

}
