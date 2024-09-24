package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long>, CustomPointRepository {

    @Query("select COALESCE(SUM(p.amount), 0) from Point p where p.user.id = :userId")
    int getCurrentPointByUserId(Long userId);

}
