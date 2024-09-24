package com.iamjunhyeok.review.dto.request;

import com.iamjunhyeok.review.dto.ReviewDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewModifyRequest {
    List<ReviewDto> reviews = new ArrayList<>();

    List<Long> deleteReviewIds = new ArrayList<>();
}
