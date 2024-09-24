package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Banner;
import com.iamjunhyeok.review.projection.BannerSimpleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>, CustomBannerRepository {

    @Query("select b.id as id, b.imageName as imageName, b.linkUrl as linkUrl from Banner b where :today between b.startDate and b.endDate")
    List<BannerSimpleProjection> findAllByToday(LocalDate today);
}
