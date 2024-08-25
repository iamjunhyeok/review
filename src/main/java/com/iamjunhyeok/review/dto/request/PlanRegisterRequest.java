package com.iamjunhyeok.review.dto.request;

import com.iamjunhyeok.review.constant.CampaignRegistrationType;
import com.iamjunhyeok.review.constant.PointPaymentType;
import com.iamjunhyeok.review.constant.ReviewerSelectionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanRegisterRequest {
    @NotBlank
    private String name;

    @PositiveOrZero
    private int campaignCount;

    @PositiveOrZero
    private int peopleCount;

    @PositiveOrZero
    private int price;

    @PositiveOrZero
    private int discountPrice;

    @PositiveOrZero
    private int reportDownloadDays;

    private CampaignRegistrationType campaignRegistrationType;
    private ReviewerSelectionType reviewerSelectionType;
    private PointPaymentType pointPaymentType;
}
