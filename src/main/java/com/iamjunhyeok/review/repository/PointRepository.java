package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long>, CustomPointRepository {
}
