package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.NoticeProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomNoticeRepository {
    List<NoticeProjection> fetchAll(Long categoryCodeId, Pageable pageable);

    NoticeProjection fetchOne(Long id);
}
