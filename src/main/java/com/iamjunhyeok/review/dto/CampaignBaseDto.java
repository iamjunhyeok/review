package com.iamjunhyeok.review.dto;

import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.constant.CampaignType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CampaignBaseDto extends AddressDto {
    @NotNull
    @Enumerated(EnumType.STRING)
    private CampaignType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CampaignCategory category;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CampaignSocial social;

    @NotBlank
    private String title;

    @NotNull
    @Positive
    private Integer capacity;

    @NotNull
    @FutureOrPresent
    private LocalDate applicationStartDate;

    @NotNull
    @Future
    private LocalDate applicationEndDate;

    @NotNull
    @Future
    private LocalDate announcementDate;

    @NotNull
    @Future
    private LocalDate reviewStartDate;

    @NotNull
    @Future
    private LocalDate reviewEndDate;

    @NotBlank
    private String offering;

    @NotBlank
    private String offeringSummary;

    @NotBlank
    private String keyword;

    @NotBlank
    private String hashtag;

    @NotBlank
    private String mission;

    @NotBlank
    private String guide;

    @NotBlank
    private String information;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @NotBlank
    private String storeInformation;
}
