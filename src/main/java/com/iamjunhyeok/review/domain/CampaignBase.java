package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.CampaignStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public class CampaignBase extends CampaignAddress {

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    @Positive
    Integer capacity;

    @Column(nullable = false)
    @FutureOrPresent
    LocalDate applicationStartDate;

    @Column(nullable = false)
    @FutureOrPresent
    LocalDate applicationEndDate;

    @Column(nullable = false)
    @Future
    LocalDate announcementDate;

    @Column(nullable = false)
    @Future
    LocalDate reviewStartDate;

    @Column(nullable = false)
    @Future
    LocalDate reviewEndDate;

    @Column(length = 2000, nullable = false)
    String offering;

    @Column(nullable = false)
    String offeringSummary;

    @Column(nullable = false)
    String keyword;

    @Column(nullable = false)
    String hashtag;

    @Column(length = 2000, nullable = false)
    String guide;

    @Column(length = 2000)
    String information;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CampaignStatus status;

    @Column(length = 2000, nullable = false)
    String storeInformation;

    @PositiveOrZero
    Integer point;
}
