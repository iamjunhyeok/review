package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateResponse {
    private Long id;
    private String receiptUrl;
    private String blogUrl;
    private String postUrl;

    public static ReviewUpdateResponse from(Review review) {
        ReviewUpdateResponse response = new ReviewUpdateResponse();
        response.setId(review.getId());
        response.setReceiptUrl(review.getReceiptUrl());
        response.setBlogUrl(review.getBlogUrl());
        response.setPostUrl(review.getPostUrl());
        return response;
    }
}
