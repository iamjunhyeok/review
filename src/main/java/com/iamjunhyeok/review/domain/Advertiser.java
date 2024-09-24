package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.AdvertiserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
public class Advertiser extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_type_code_id")
    private Code businessTypeCode;

    @Column(nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private String businessName;

    private String businessLicenseImageName;

    private String homepageUrl;

    private String taxInvoiceEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdvertiserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
