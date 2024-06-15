package com.iamjunhyeok.review.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PenaltyReason {
    USER_CANCELLED("사용자 단순변심으로 취소", -2),
    REVIEW_PERIOD_EXCEEDED("리뷰 기간 초과", -1),
    REVIEW_NOT_SUBMITTED("리뷰 미등록", -2),
    HOST_CANCELLED("업체측 사유로 인한 취소", 1);

    private String reason;
    private int point;
}
