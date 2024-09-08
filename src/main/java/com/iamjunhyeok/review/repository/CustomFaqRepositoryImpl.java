package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.constant.FaqCategory;
import com.iamjunhyeok.review.domain.Faq;
import com.iamjunhyeok.review.projection.FaqProjection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CustomFaqRepositoryImpl implements CustomFaqRepository {

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    @Override
    public List<FaqProjection> fetchAll(String category, Pageable pageable) {
        CriteriaBuilder<Faq> cb = getFaqCriteriaBuilder(category);
        cb.setMaxResults(pageable.getPageSize());

        EntityViewSetting<FaqProjection, CriteriaBuilder<FaqProjection>> setting = EntityViewSetting.create(FaqProjection.class);
        return entityViewManager.applySetting(setting, cb).getResultList();
    }

    @Override
    public Long fetchAll(String category) {
        CriteriaBuilder<Faq> faqCriteriaBuilder = getFaqCriteriaBuilder(category);
        TypedQuery<Long> countQuery = faqCriteriaBuilder.getCountQuery();
        return countQuery.getSingleResult();
    }

    private CriteriaBuilder<Faq> getFaqCriteriaBuilder(String category) {
        CriteriaBuilder<Faq> cb = criteriaBuilderFactory.create(entityManager, Faq.class, "f");
        if (Strings.isNotBlank(category)) {
            cb.where("f.category").eq(FaqCategory.valueOf(category.toUpperCase()));
        }
        return cb;
    }
}
