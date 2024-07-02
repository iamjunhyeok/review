package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateResponse {
    private Long id;
    private String receiptUrl;
    private String blogUrl;
    private String postUrl;

    public static ReviewCreateResponse from(Review review) {
        ReviewCreateResponse response = new ReviewCreateResponse();
        response.setId(review.getId());
        response.setReceiptUrl(review.getReceiptUrl());
        response.setBlogUrl(review.getBlogUrl());
        response.setPostUrl(review.getPostUrl());
        return response;
    }
}
