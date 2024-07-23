package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.projection.InquiryProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RequiredArgsConstructor
public class CustomInquiryRepositoryImpl implements CustomInquiryRepository {

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    @Override
    public List<InquiryProjection> fetchAllInquiriesForAuthenticatedUser(String category) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        CriteriaBuilder<Inquiry> cb = criteriaBuilderFactory.create(entityManager, Inquiry.class, "i");
        cb.where("i.user.id").eq(principal.getUserId());
        if (Strings.isNotBlank(category)) cb.where("i.category").eq(category);
        return entityViewManager.applySetting(EntityViewSetting.create(InquiryProjection.class), cb).getResultList();
    }

    @Override
    public List<InquiryProjection> fetchAllInquiries(String category) {
        CriteriaBuilder<Inquiry> cb = criteriaBuilderFactory.create(entityManager, Inquiry.class, "i");
        if (Strings.isNotBlank(category)) cb.where("i.category").eq(category);
        return entityViewManager.applySetting(EntityViewSetting.create(InquiryProjection.class), cb).getResultList();
    }
}
