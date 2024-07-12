package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.ReviewType;
import com.iamjunhyeok.review.domain.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Long id;
    private ReviewType type;
    private String url;

    public static ReviewDto from(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setType(review.getType());
        dto.setUrl(review.getUrl());
        return dto;
    }
}
