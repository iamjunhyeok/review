package com.iamjunhyeok.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataDictionaryCreateRequest {
    private Long parentId;
    private String name;
    private String value;
    private Integer order;
}
