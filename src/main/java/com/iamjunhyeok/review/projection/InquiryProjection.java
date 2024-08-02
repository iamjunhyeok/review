package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.iamjunhyeok.review.constant.InquiryCategory;
import com.iamjunhyeok.review.domain.Inquiry;

import java.time.LocalDateTime;

@EntityView(Inquiry.class)
public interface InquiryProjection {
    Long getId();
    InquiryCategory getCategory();
    String getTitle();
    String getContent();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}
