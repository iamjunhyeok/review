package com.iamjunhyeok.review.projection;

import com.iamjunhyeok.review.constant.PointReason;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PointProjection {
    private Long id;
    private int amount;
    private PointReason reason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
