package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.BannerProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomBannerRepository {
    List<BannerProjection> fetchAll(Pageable pageable);

    List<BannerProjection> fetchAllValidBanners();
}
