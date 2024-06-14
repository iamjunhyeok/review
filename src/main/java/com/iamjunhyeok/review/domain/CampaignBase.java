package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.CampaignSocial;
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
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class CampaignBase {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CampaignType type;

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

    // ---- 신청기간
    @NotNull
    @Column(nullable = false)
    @FutureOrPresent
    LocalDate applicationStartDate;

    @NotNull
    @Column(nullable = false)
    @Future
    LocalDate applicationEndDate;

    // --- 발표일
    @NotNull
    @Column(nullable = false)
    @Future
    LocalDate announcementDate;

    // --- 이용기간
    @NotNull
    @Column(nullable = false)
    @Future
    LocalDate useStartDate;

    @NotNull
    @Column(nullable = false)
    @Future
    LocalDate useEndDate;

    // --- 리뷰기간
    @NotNull
    @Column(nullable = false)
    @Future
    LocalDate reviewStartDate;

    @NotNull
    @Column(nullable = false)
    @Future
    LocalDate reviewEndDate;

    // --- 제공 내용
    @Column(nullable = false)
    @NotBlank
    String offering;

    // --- 필수 키워드
    @Column(nullable = false)
    @NotBlank
    String keyword;

    // --- 해시태그
    @Column(nullable = false)
    @NotBlank
    String hashtag;

    // --- 미션
    @Column(nullable = false)
    @NotBlank
    String mission;

    // --- 작성 가이드
    @Column(nullable = false)
    @NotBlank
    String guide;

    // --- 안내사항
    @Column(nullable = false)
    @NotBlank
    String information;
}
