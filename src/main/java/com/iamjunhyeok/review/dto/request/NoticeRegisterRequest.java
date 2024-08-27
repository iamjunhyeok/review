package com.iamjunhyeok.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRegisterRequest {
    private String categoryCode;
    private String title;
    private String content;
}
