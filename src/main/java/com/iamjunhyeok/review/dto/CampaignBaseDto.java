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
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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
    @FutureOrPresent
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
    private String guide;

    private String information;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @NotBlank
    private String storeInformation;

    @PositiveOrZero
    private Integer point;

    private List<CampaignMissionDto> missions;

    private List<CampaignOptionDto> options;
}
