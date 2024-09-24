package com.iamjunhyeok.review.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iamjunhyeok.review.constant.CampaignStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignProjection {
    private Long id;
    private CodeProjection typeCode;
    private CodeProjection categoryCode;
    private CodeProjection socialCode;
    private String title;
    private Integer capacity;
    private LocalDate applicationStartDate;
    private LocalDate applicationEndDate;
    private LocalDate announcementDate;
    private LocalDate reviewStartDate;
    private LocalDate reviewEndDate;
    private String offering;
    private String offeringSummary;
    private String keyword;
    private String hashtag;
    private String guide;
    private String information;
    private CampaignStatus status;
    private String storeInformation;
    private Integer point;
    private String address;
    private String rest;
    private String postalCode;
    private String longitude;
    private String latitude;
    private Set<CampaignLinkProjection> links;
    private Set<CampaignImageProjection> images;
    private Set<CampaignMissionProjection> missions;
    private Set<CampaignOptionProjection> options;

    private long applicantsCount;
    private int dDay;
    private boolean isFavourite;
}