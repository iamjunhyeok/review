package com.iamjunhyeok.review.projection;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BannerProjection {
    private Long id;
    private String imageName;
    private String linkUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private int order;
    private boolean deleted;
    private CodeProjection screenCode;
}
