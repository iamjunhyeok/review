package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.Banner;
import com.iamjunhyeok.review.projection.BannerProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomBannerRepositoryImpl implements CustomBannerRepository {

    private final CriteriaBuilderFactory cbf;

    private final EntityManager em;

    private final EntityViewManager evm;

    @Override
    public List<BannerProjection> fetchAll() {
        CriteriaBuilder<Banner> cb = cbf.create(em, Banner.class);
        return evm.applySetting(EntityViewSetting.create(BannerProjection.class), cb).getResultList();
    }
}
