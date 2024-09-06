package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.RegionGroup;
import com.iamjunhyeok.review.projection.RegionGroupProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionGroupRepository extends JpaRepository<RegionGroup, Long> {

    @Query("select rg from RegionGroup rg")
    List<RegionGroupProjection> fetchAll();
}
