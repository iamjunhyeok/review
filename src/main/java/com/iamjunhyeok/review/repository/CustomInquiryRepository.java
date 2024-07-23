package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.InquiryProjection;

import java.util.List;

public interface CustomInquiryRepository {

    List<InquiryProjection> fetchAllInquiriesForAuthenticatedUser(String category);

    List<InquiryProjection> fetchAllInquiries(String category);
}
