package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataDictionaryUpdateRequest {
    private String name;
    private String value;
    private Integer order;
}
