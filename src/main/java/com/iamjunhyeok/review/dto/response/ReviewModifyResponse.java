package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.domain.Review;
import com.iamjunhyeok.review.dto.ReviewDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewModifyResponse {
    private List<ReviewDto> reviews = new ArrayList<>();

    public static ReviewModifyResponse from(List<Review> reviews) {
        ReviewModifyResponse response = new ReviewModifyResponse();
        response.setReviews(
                reviews.stream().map(ReviewDto::from).toList()
        );
        return response;
    }
}
