package com.iamjunhyeok.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @NotBlank
    private String address;

    private String rest;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String longitude;

    @NotBlank
    private String latitude;
}
