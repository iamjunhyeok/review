package com.iamjunhyeok.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionGroupRegisterRequest {
    private String name;
    private String description;
    private int order;
}
