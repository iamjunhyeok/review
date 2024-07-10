package com.iamjunhyeok.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    @NotBlank
    private String address;

    @NotBlank
    private String rest;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String longitude;

    @NotBlank
    private String latitude;
}
