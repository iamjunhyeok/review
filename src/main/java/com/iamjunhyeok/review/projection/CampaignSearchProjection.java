package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingSubquery;
import com.blazebit.persistence.view.SubqueryProvider;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignType;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Campaign;

import java.time.LocalDate;
import java.util.List;

@EntityView(Campaign.class)
public interface CampaignSearchProjection {
    @IdMapping
    Long getId();

    CampaignType getType();
    CampaignCategory getCategory();
    CampaignSocial getSocial();

    String getTitle();
    Integer getCapacity();

    LocalDate getApplicationEndDate();

    LocalDate getAnnouncementDate();

    String getOfferingSummary();

    @MappingSubquery(ApplicantsCountSubqueryProvider.class)
    Long getApplicantsCount();

    String getLongitude();
    String getLatitude();

    List<CampaignImageProjection> getImages();

    List<CampaignOptionProjection> getOptions();

    @Mapping("DATEDIFF(applicationEndDate, CURDATE())")
    Integer getDday();

    @Mapping("CASE WHEN favourites IS NOT NULL THEN TRUE ELSE FALSE END")
    boolean getIsFavourite();

    class ApplicantsCountSubqueryProvider implements SubqueryProvider {
        @Override
        public <T> T createSubquery(SubqueryInitiator<T> subqueryBuilder) {
            return subqueryBuilder.from(Application.class, "a")
                    .select("COUNT(*)")
                    .where("a.campaign.id").eqExpression("EMBEDDING_VIEW(id)")
                    .where("a.status").notEq(ApplicationStatus.CANCELLED)
                    .end();
        }
    }
}
