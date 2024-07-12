package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewCreateResponse {
    List<ReviewDto> reviews = new ArrayList<>();

    public static ReviewCreateResponse from(List<Review> reviews) {
        ReviewCreateResponse response = new ReviewCreateResponse();
        response.setReviews(
                reviews.stream().map(ReviewDto::from).toList()
        );
        return response;
    }
}
