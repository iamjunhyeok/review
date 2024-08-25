package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.constant.CampaignRegistrationType;
import com.iamjunhyeok.review.constant.PointPaymentType;
import com.iamjunhyeok.review.constant.ReviewerSelectionType;
import com.iamjunhyeok.review.domain.Plan;

@EntityView(Plan.class)
public interface PlanProjection {
    Long getId();

    String getName();

    int getCampaignCount();

    int getPeopleCount();

    int getPrice();

    int getDiscountPrice();

    int getReportDownloadDays();

    CampaignRegistrationType getCampaignRegistrationType();

    ReviewerSelectionType getReviewerSelectionType();

    PointPaymentType getPointPaymentType();
}
