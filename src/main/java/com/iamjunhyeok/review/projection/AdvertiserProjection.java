package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.AdvertiserStatus;
import com.iamjunhyeok.review.domain.Advertiser;

@EntityView(Advertiser.class)
public interface AdvertiserProjection {
    @IdMapping
    Long getId();

    String getBusinessTypeCode();

//    String getBusinessTypeName();

    String getBusinessNumber();

    String getBusinessLicenseImageName();

    String getHomepageUrl();

    String getTaxInvoiceEmail();

    String getAccessSourceCode();

//    String getAccessSourceName();

    AdvertiserStatus getStatus();

    UserProjection getUser();
}
