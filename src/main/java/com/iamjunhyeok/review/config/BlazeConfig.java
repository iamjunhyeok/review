package com.iamjunhyeok.review.config;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViews;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import com.iamjunhyeok.review.dto.CampaignImageProjection;
import com.iamjunhyeok.review.dto.CampaignLinkProjection;
import com.iamjunhyeok.review.repository.CampaignViewProjection;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BlazeConfig {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public CriteriaBuilderFactory createCriteriaBuilderFactory() {
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        return config.createCriteriaBuilderFactory(entityManagerFactory);
    }

    @Bean
    public EntityViewManager entityViewManager(CriteriaBuilderFactory criteriaBuilderFactory) {
        EntityViewConfiguration config = EntityViews.createDefaultConfiguration();
        config.addEntityView(CampaignViewProjection.class);
        config.addEntityView(CampaignLinkProjection.class);
        config.addEntityView(CampaignImageProjection.class);
        return config.createEntityViewManager(criteriaBuilderFactory);
    }
}
