package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.domain.Penalty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PenaltySearchResponse {
    private String reason;
    private int point;

    public static PenaltySearchResponse from(Penalty penalty) {
        PenaltySearchResponse response = new PenaltySearchResponse();
        response.setReason(penalty.getReason().getReason());
        response.setPoint(penalty.getPoint());
        return response;
    }
}
