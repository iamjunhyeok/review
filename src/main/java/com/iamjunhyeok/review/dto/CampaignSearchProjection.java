package com.iamjunhyeok.review.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
public class CampaignSearchProjection {
    private CampaignType type;
    private CampaignSocial social;
    private String title;
    private Integer capacity;

    @JsonIgnore
    LocalDate applicationEndDate;

    private int dDay;
    private Long applicantsCount;

    public int getdDay() {
        return Period.between(LocalDate.now(), applicationEndDate).getDays();
    }
}
