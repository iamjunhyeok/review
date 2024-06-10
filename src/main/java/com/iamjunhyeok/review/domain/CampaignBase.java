package com.iamjunhyeok.review.domain;

import jakarta.persistence.MappedSuperclass;
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

    protected String title;

    private Integer capacity;

    // ---- 신청기간
    private LocalDate applicationStartDate;
    private LocalDate applicationEndDate;

    // --- 발표일
    private LocalDate announcementDate;

    // --- 이용기간
    private LocalDate useStartDate;
    private LocalDate useEndDate;

    // --- 리뷰기간
    private LocalDate reviewStartDate;
    private LocalDate reviewEndDate;

    // --- 제공 내용
    private String offering;

    // --- 필수 키워드
    private String keyword;

    // --- 해시태그
    private String hashtag;

    // --- 미션
    private String mission;

    // --- 작성 가이드
    private String guide;

    // --- 안내사항
    private String information;
}
