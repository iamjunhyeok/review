package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.CampaignRegistrationType;
import com.iamjunhyeok.review.constant.PointPaymentType;
import com.iamjunhyeok.review.constant.ReviewerSelectionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Plan extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int campaignCount;

    @Column(nullable = false)
    private int peopleCount;

    @Column(nullable = false)
    private int price;

    private int discountPrice;

    @Column(nullable = false)
    private int reportDownloadDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignRegistrationType campaignRegistrationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewerSelectionType reviewerSelectionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointPaymentType pointPaymentType;
}
