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

    String title;

    Integer capacity;

    // ---- 신청기간
    LocalDate applicationStartDate;
    LocalDate applicationEndDate;

    // --- 발표일
    LocalDate announcementDate;

    // --- 이용기간
    LocalDate useStartDate;
    LocalDate useEndDate;

    // --- 리뷰기간
    LocalDate reviewStartDate;
    LocalDate reviewEndDate;

    // --- 제공 내용
    String offering;

    // --- 필수 키워드
    String keyword;

    // --- 해시태그
    String hashtag;

    // --- 미션
    String mission;

    // --- 작성 가이드
    String guide;

    // --- 안내사항
    String information;
}
