package com.iamjunhyeok.review.projection;

import com.iamjunhyeok.review.constant.PenaltyReason;
import lombok.Getter;

@Getter
public class PenaltyProjection {
    private Long id;
    private PenaltyReason reason;
    private String details;
    private int point;
    private ApplicationProjection application;
}
