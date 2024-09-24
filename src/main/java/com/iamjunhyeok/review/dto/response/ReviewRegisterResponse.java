package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.domain.Review;
import com.iamjunhyeok.review.dto.ReviewDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewRegisterResponse {
    List<ReviewDto> reviews = new ArrayList<>();

    public static ReviewRegisterResponse from(List<Review> reviews) {
        ReviewRegisterResponse response = new ReviewRegisterResponse();
        response.setReviews(
                reviews.stream().map(ReviewDto::from).toList()
        );
        return response;
    }
}
