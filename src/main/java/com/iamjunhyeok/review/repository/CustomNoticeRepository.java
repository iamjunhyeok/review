package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.NoticeProjection;

import java.util.List;

public interface CustomNoticeRepository {
    List<NoticeProjection> fetchAll();
}
