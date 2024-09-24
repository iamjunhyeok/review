package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.Banner;

import java.time.LocalDate;

@EntityView(Banner.class)
public interface BannerView {
    @IdMapping
    Long getId();

    String getImageName();

    String getLinkUrl();

    LocalDate getStartDate();

    LocalDate getEndDate();

    int getOrder();

    boolean getDeleted();

    CodeView getScreen();
}
