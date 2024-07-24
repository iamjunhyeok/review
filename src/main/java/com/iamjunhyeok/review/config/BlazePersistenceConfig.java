package com.iamjunhyeok.review.config;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViews;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import com.iamjunhyeok.review.projection.ApplicantProjection;
import com.iamjunhyeok.review.projection.CampaignImageProjection;
import com.iamjunhyeok.review.projection.CampaignLinkProjection;
import com.iamjunhyeok.review.projection.CampaignMissionProjection;
import com.iamjunhyeok.review.projection.CampaignOptionProjection;
import com.iamjunhyeok.review.projection.CampaignSearchProjection;
import com.iamjunhyeok.review.projection.CampaignSummaryProjection;
import com.iamjunhyeok.review.projection.CampaignViewProjection;
import com.iamjunhyeok.review.projection.CodeProjection;
import com.iamjunhyeok.review.projection.FaqProjection;
import com.iamjunhyeok.review.projection.InquiryProjection;
import com.iamjunhyeok.review.projection.PenaltyProjection;
import com.iamjunhyeok.review.projection.PointSearchProjection;
import com.iamjunhyeok.review.projection.ReviewProjection;
import com.iamjunhyeok.review.projection.UserCampaignApplicationProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import com.iamjunhyeok.review.projection.UserProjection;
import com.iamjunhyeok.review.projection.UserSearchProjection;
import com.iamjunhyeok.review.projection.UserViewProjection;
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
        config.addEntityView(UserCampaignSearchProjection.class);
        config.addEntityView(UserCampaignSearchProjection.ApplicationProjection.class);
        config.addEntityView(UserCampaignApplicationProjection.class);
        config.addEntityView(PointSearchProjection.class);
        config.addEntityView(PointSearchProjection.ApplicationProjection.class);
        config.addEntityView(PointSearchProjection.ApplicationProjection.CampaignProjection.class);
        config.addEntityView(CampaignSearchProjection.class);
        config.addEntityView(CampaignMissionProjection.class);
        config.addEntityView(CampaignOptionProjection.class);
        config.addEntityView(CodeProjection.class);
        config.addEntityView(InquiryProjection.class);
        config.addEntityView(ApplicantProjection.class);
        config.addEntityView(UserProjection.class);
        config.addEntityView(FaqProjection.class);
        config.addEntityView(PenaltyProjection.class);
        config.addEntityView(ReviewProjection.class);
        return config.createEntityViewManager(cbf);
    }
}
