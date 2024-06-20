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
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class CampaignBase extends CampaignAddress {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignSocial social;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @NotNull
    @Column(nullable = false)
    @Positive
    private Integer capacity;

    // ---- 신청기간
    @NotNull
    @Column(nullable = false)
    @FutureOrPresent
    private LocalDate applicationStartDate;

    @NotNull
    @Column(nullable = false)
    @Future
    private LocalDate applicationEndDate;

    // --- 발표일
    @NotNull
    @Column(nullable = false)
    @Future
    private LocalDate announcementDate;

    // --- 이용기간
    @NotNull
    @Column(nullable = false)
    @Future
    private LocalDate useStartDate;

    @NotNull
    @Column(nullable = false)
    @Future
    private LocalDate useEndDate;

    // --- 리뷰기간
    @NotNull
    @Column(nullable = false)
    @Future
    private LocalDate reviewStartDate;

    @NotNull
    @Column(nullable = false)
    @Future
    private LocalDate reviewEndDate;

    // --- 제공 내용
    @Column(nullable = false)
    @NotBlank
    private String offering;

    // --- 필수 키워드
    @Column(nullable = false)
    @NotBlank
    private String keyword;

    // --- 해시태그
    @Column(nullable = false)
    @NotBlank
    private String hashtag;

    // --- 미션
    @Column(nullable = false)
    @NotBlank
    private String mission;

    // --- 작성 가이드
    @Column(nullable = false)
    @NotBlank
    private String guide;

    // --- 안내사항
    @Column(nullable = false)
    @NotBlank
    private String information;
}
