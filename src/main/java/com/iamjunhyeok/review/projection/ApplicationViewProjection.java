package com.iamjunhyeok.review.projection;

import com.iamjunhyeok.review.constant.ApplicationReason;
import com.iamjunhyeok.review.constant.ApplicationStatus;

public interface ApplicationViewProjection {
    Long getId();

    String getName();

    String getPhoneNumber();

    String getAddress();

    String getRest();

    String getPostalCode();

    ApplicationStatus getStatus();

    ApplicationReason getReason();

    String getDetails();

    CampaignSummaryProjection getCampaign();
}
