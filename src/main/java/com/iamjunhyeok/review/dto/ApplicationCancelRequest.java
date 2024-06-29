package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.ApplicationReason;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationCancelRequest {
    @NotNull
    private ApplicationReason reason;
    private String details;
}
