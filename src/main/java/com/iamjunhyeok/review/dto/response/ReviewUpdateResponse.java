package com.iamjunhyeok.review.dto.response;

import com.iamjunhyeok.review.domain.Review;
import com.iamjunhyeok.review.dto.ReviewDto;
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
