package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingSubquery;
import com.blazebit.persistence.view.SubqueryProvider;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Campaign;

import java.time.LocalDate;
import java.util.List;

@EntityView(Campaign.class)
public interface CampaignViewProjection {
    @IdMapping
    Long getId();

    CodeView getTypeCode();

    CodeView getCategoryCode();

    CodeView getSocialCode();

    String getTitle();

    Integer getCapacity();

    LocalDate getApplicationStartDate();

    LocalDate getApplicationEndDate();

    LocalDate getAnnouncementDate();

    LocalDate getReviewStartDate();

    LocalDate getReviewEndDate();

    String getOffering();

    String getOfferingSummary();

    String getKeyword();

    String getHashtag();

    String getGuide();

    String getInformation();

    CampaignStatus getStatus();

    String getStoreInformation();

    Integer getPoint();

    String getAddress();

    String getRest();

    String getPostalCode();

    String getLongitude();

    String getLatitude();

    List<CampaignLinkView> getLinks();

    List<CampaignImageView> getImages();

    List<CampaignMissionView> getMissions();

    List<CampaignOptionView> getOptions();

    @Mapping("CASE WHEN favourites IS NOT NULL THEN TRUE ELSE FALSE END")
    boolean getIsFavourite();

    @MappingSubquery(ApplicantsCountSubqueryProvider.class)
    Long getApplicantsCount();

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
