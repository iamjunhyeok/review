package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.FaqProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomFaqRepository {

    List<FaqProjection> fetchAll(Long categoryId, Pageable pageable);

    Long fetchAll(Long categoryId);

    FaqProjection fetchOne(Long id);
}
