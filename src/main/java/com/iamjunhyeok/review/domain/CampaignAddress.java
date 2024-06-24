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
    private String address;

    @NotBlank
    @Column(nullable = false)
    private String rest;

    @NotBlank
    @Column(nullable = false)
    private String postalCode;

    @NotBlank
    @Column(nullable = false)
    private String longitude;

    @NotBlank
    @Column(nullable = false)
    private String latitude;
}
