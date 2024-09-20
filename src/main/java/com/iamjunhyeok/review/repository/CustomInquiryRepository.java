package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.InquiryProjection;
import com.iamjunhyeok.review.projection.InquiryWithAnswerAndUserProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomInquiryRepository {

    List<InquiryWithAnswerAndUserProjection> fetchAllInquiriesForAuthenticatedUser(String category);

    List<InquiryWithAnswerAndUserProjection> fetchAllInquiries(String category);

    List<InquiryProjection> fetchAllInquiriesByUserId(Long userId, Pageable pageable);

    List<InquiryProjection> fetchAllInquiries(Pageable pageable);
}
