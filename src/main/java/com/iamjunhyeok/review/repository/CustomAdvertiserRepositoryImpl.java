package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.constant.AdvertiserStatus;
import com.iamjunhyeok.review.domain.Advertiser;
import com.iamjunhyeok.review.projection.AdvertiserProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

@RequiredArgsConstructor
public class CustomAdvertiserRepositoryImpl implements CustomAdvertiserRepository {

    private final CriteriaBuilderFactory cbf;

    private final EntityManager em;

    private final EntityViewManager evm;

    @Override
    public List<AdvertiserProjection> fetchAll(String status) {
        CriteriaBuilder<Advertiser> cb = cbf.create(em, Advertiser.class, "a");
        if (Strings.isNotBlank(status)) {
            cb.where("a.status").eq(AdvertiserStatus.valueOf(status.toUpperCase()));
        }
        return evm.applySetting(EntityViewSetting.create(AdvertiserProjection.class), cb).getResultList();
    }
}
