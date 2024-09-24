package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.MappingSubquery;
import com.blazebit.persistence.view.SubqueryProvider;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.constant.ReviewStatus;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Review;

@EntityView(Application.class)
public interface ApplicationSearchProjection {
    @IdMapping
    Long getId();

    String getName();

    String getPhoneNumber();

    String getAddress();

    String getRest();

    String getPostalCode();

    ApplicationStatus getStatus();

    CampaignSummaryView getCampaign();

    @MappingSubquery(ModifyRequestReviewCountSubqueryProvider.class)
    Long getModifyRequestReviewCount();

    class ModifyRequestReviewCountSubqueryProvider implements SubqueryProvider {

        @Override
        public <T> T createSubquery(SubqueryInitiator<T> subqueryInitiator) {
            return subqueryInitiator.from(Review.class, "r")
                    .select("COUNT(*)")
                    .where("r.application.id").eqExpression("EMBEDDING_VIEW(id)")
                    .where("r.status").eq(ReviewStatus.MODIFY_REQUEST)
                    .end();
        }
    }
}
