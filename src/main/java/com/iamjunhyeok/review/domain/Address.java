package com.iamjunhyeok.review.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Address extends Base {
    private String address;

    private String rest;

    private String postalCode;
}
