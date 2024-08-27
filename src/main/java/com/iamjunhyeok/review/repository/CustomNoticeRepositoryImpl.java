package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.Notice;
import com.iamjunhyeok.review.projection.NoticeProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomNoticeRepositoryImpl implements CustomNoticeRepository {

    private final CriteriaBuilderFactory cbf;

    private final EntityManager em;

    private final EntityViewManager evm;

    @Override
    public List<NoticeProjection> fetchAll() {
        CriteriaBuilder<Notice> cb = cbf.create(em, Notice.class);
        return evm.applySetting(EntityViewSetting.create(NoticeProjection.class), cb).getResultList();
    }
}
