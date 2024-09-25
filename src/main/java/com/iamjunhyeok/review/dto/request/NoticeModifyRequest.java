package com.iamjunhyeok.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeModifyRequest {
    private Long categoryCodeId;
    private String title;
    private String content;
}
