package com.iamjunhyeok.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Address extends Base {
    private String address;

    private String rest;

    @Column(length = 5)
    private String postalCode;

}
