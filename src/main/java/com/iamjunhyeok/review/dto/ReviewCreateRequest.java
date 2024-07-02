package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {
    private String receiptUrl;
    private String blogUrl;
    private String postUrl;
}
