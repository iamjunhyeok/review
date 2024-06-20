package com.iamjunhyeok.review.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class Address extends Base {
    private String address;

    private String rest;

    private String postalCode;
}
