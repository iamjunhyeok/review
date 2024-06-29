package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.PenaltyReason;
import com.iamjunhyeok.review.domain.Penalty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PenaltySearchResponse {
    private PenaltyReason reason;
    private int point;
    private CampaignDto campaign = new CampaignDto();

    public static PenaltySearchResponse from(Penalty penalty) {
        PenaltySearchResponse response = new PenaltySearchResponse();
        response.setReason(penalty.getReason());
        response.setPoint(penalty.getPoint());
        response.getCampaign().setTitle(penalty.getApplication().getCampaign().getTitle());
        response.getCampaign().setReviewStartDate(penalty.getApplication().getCampaign().getReviewStartDate());
        response.getCampaign().setReviewEndDate(penalty.getApplication().getCampaign().getReviewEndDate());
        return response;
    }

    @Getter
    @Setter
    private class CampaignDto {
        private String title;
        private LocalDate reviewStartDate;
        private LocalDate reviewEndDate;
    }
}
