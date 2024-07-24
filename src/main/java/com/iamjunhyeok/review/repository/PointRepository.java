package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Point;
import com.iamjunhyeok.review.projection.PointProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long>, CustomPointRepository {

    List<PointProjection> findByUserId(Long userId);
}
