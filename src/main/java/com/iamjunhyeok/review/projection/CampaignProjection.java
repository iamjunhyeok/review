package com.iamjunhyeok.review.projection;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CampaignProjection {
    private Long id;
    private CodeProjection typeCode;
    private CodeProjection categoryCode;
    private CodeProjection socialCode;
    private String title;
    private Integer capacity;
    private LocalDate applicationEndDate;
    private LocalDate announcementDate;
    private String offeringSummary;
    private String longitude;
    private String latitude;
    private List<CampaignImageProjection> images;

    private long applicantsCount;
    private int dDay;
}