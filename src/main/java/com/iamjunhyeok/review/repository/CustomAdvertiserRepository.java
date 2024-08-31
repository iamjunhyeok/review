package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.AdvertiserProjection;

import java.util.List;

public interface CustomAdvertiserRepository {

    List<AdvertiserProjection> fetchAll(String status);
}
