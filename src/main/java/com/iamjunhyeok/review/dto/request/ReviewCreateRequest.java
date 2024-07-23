package com.iamjunhyeok.review.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewCreateRequest {
    private List<ReviewDto> reviews = new ArrayList<>();
}
