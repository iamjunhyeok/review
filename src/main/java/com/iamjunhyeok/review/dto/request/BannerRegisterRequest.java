package com.iamjunhyeok.review.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BannerRegisterRequest {
    private String linkUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private int order;
}
