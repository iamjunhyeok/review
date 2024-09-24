package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.InquiryProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomInquiryRepository {

    List<InquiryProjection> fetchAllInquiriesByUserId(Long userId, Pageable pageable);

    List<InquiryProjection> fetchAll(Long categoryCodeId, Pageable pageable);

    InquiryProjection fetchOne(Long id);
}
