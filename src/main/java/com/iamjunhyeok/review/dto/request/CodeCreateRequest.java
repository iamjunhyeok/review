package com.iamjunhyeok.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeCreateRequest {
    private String code;
    private String value;
    private Integer order;
    private Long parentId;
}
