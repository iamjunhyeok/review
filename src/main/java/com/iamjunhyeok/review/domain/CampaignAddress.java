package com.iamjunhyeok.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public class CampaignAddress {

    @NotBlank
    @Column(nullable = false)
    String address;

    @NotBlank
    @Column(nullable = false)
    String rest;

    @NotBlank
    @Column(nullable = false)
    String postalCode;

    @NotBlank
    @Column(nullable = false)
    String longitude;

    @NotBlank
    @Column(nullable = false)
    String latitude;
}
