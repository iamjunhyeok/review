package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewUpdateResponse {
    private List<ReviewDto> reviews = new ArrayList<>();

    public static ReviewUpdateResponse from(List<Review> reviews) {
        ReviewUpdateResponse response = new ReviewUpdateResponse();
        response.setReviews(
                reviews.stream().map(ReviewDto::from).toList()
        );
        return response;
    }
}
