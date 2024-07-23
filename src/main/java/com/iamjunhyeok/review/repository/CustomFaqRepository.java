package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.FaqProjection;

import java.util.List;

public interface CustomFaqRepository {

    List<FaqProjection> fetchAll(String category);
}
