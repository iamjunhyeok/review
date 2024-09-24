package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.domain.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRejectResponse {
    private Long id;
    private String details;

    public static ReviewRejectResponse from(Review review) {
        ReviewRejectResponse response = new ReviewRejectResponse();
        response.setId(review.getId());
        response.setDetails(review.getDetails());
        return response;
    }
}
