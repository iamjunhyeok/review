package com.iamjunhyeok.review.projection;

import lombok.Getter;

@Getter
public class NoticeProjection {
    private Long id;
    private CodeProjection categoryCode;
    private String title;
    private String content;
}
