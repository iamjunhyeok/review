package com.iamjunhyeok.review.dto.request;

import com.iamjunhyeok.review.constant.PenaltyReason;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PenaltyCreateRequest {

    @NotNull
    private PenaltyReason reason;

    @NotNull
    private Long campaignId;
}
