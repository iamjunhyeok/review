package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.BannerProjection;

import java.util.List;

public interface CustomBannerRepository {
    List<BannerProjection> fetchAll();
}
