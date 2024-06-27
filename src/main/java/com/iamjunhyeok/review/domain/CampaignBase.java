package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.constant.CampaignType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class CampaignBase extends CampaignAddress {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CampaignType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CampaignCategory category;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CampaignSocial social;

    @Column(nullable = false)
    @NotBlank
    String title;

    @NotNull
    @Column(nullable = false)
    @Positive
    Integer capacity;

    @NotNull
    @Column(nullable = false)
    @FutureOrPresent
    LocalDate applicationStartDate;

    @NotNull
    @Column(nullable = false)
    @Future
    LocalDate applicationEndDate;

    @NotNull
    @Column(nullable = false)
    @Future
    LocalDate announcementDate;

    @NotNull
    @Column(nullable = false)
    @Future
    LocalDate reviewStartDate;

    @NotNull
    @Column(nullable = false)
    @Future
    LocalDate reviewEndDate;

    @Column(nullable = false)
    @NotBlank
    String offering;

    @Column(nullable = false)
    @NotBlank
    String keyword;

    @Column(nullable = false)
    @NotBlank
    String hashtag;

    @Column(nullable = false)
    @NotBlank
    String mission;

    @Column(nullable = false)
    @NotBlank
    String guide;

    @Column(nullable = false)
    @NotBlank
    String information;

    @Column(nullable = false)
    CampaignStatus status;
}
