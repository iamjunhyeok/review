package com.iamjunhyeok.review.config;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViews;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import com.iamjunhyeok.review.dto.CampaignImageProjection;
import com.iamjunhyeok.review.dto.CampaignLinkProjection;
import com.iamjunhyeok.review.dto.CampaignSummaryProjection;
import com.iamjunhyeok.review.dto.CampaignViewProjection;
import com.iamjunhyeok.review.dto.UserSearchProjection;
import com.iamjunhyeok.review.dto.UserViewProjection;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

//@EnableEntityViews(basePackages = "com.iamjunhyeok.review.dto")
//@EnableJpaRepositories(basePackages = "com.iamjunhyeok.review.repository",
//        repositoryFactoryBeanClass = BlazePersistenceRepositoryFactoryBean.class)
@Configuration
public class BlazePersistenceConfig {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Lazy(false)
    public CriteriaBuilderFactory createCriteriaBuilderFactory() {
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        return config.createCriteriaBuilderFactory(entityManagerFactory);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Lazy(false)
    public EntityViewManager createEntityViewManager(CriteriaBuilderFactory cbf) {
        EntityViewConfiguration config = EntityViews.createDefaultConfiguration();
        config.addEntityView(CampaignViewProjection.class);
        config.addEntityView(CampaignLinkProjection.class);
        config.addEntityView(CampaignImageProjection.class);
        config.addEntityView(CampaignSummaryProjection.class);
        config.addEntityView(UserSearchProjection.class);
        config.addEntityView(UserViewProjection.class);
        return config.createEntityViewManager(cbf);
    }
}
