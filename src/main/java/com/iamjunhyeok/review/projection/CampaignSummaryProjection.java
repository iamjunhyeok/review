package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.Campaign;

import java.time.LocalDate;
import java.util.List;

@EntityView(Campaign.class)
public interface CampaignSummaryProjection {
    @IdMapping
    Long getId();

    CodeView getTypeCode();

    CodeView getCategoryCode();

    CodeView getSocialCode();

    String getTitle();

    LocalDate getAnnouncementDate();

    LocalDate getReviewStartDate();

    LocalDate getReviewEndDate();

    List<CampaignImageView> getImages();
}
