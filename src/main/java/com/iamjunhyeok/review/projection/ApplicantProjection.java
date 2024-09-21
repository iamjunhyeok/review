package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.MappingSubquery;
import com.blazebit.persistence.view.SubqueryProvider;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Penalty;

import java.time.LocalDateTime;

@EntityView(Application.class)
public interface ApplicantProjection {
    @IdMapping
    Long getId();

    String getName();

    String getPhoneNumber();

    String getAddress();

    String getRest();

    String getPostalCode();

    ApplicationStatus getStatus();

    @MappingSubquery(PenaltyPointsSubqueryProvider.class)
    Long getPenaltyPoints();

    UserSimpleView getUser();

    CampaignView getCampaign();

    class PenaltyPointsSubqueryProvider implements SubqueryProvider {
        @Override
        public <T> T createSubquery(SubqueryInitiator<T> subqueryInitiator) {
            LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
            return subqueryInitiator.from(Penalty.class, "p")
                    .select("COALESCE(SUM(p.point), 0)")
                    .where("p.user.id").eqExpression("EMBEDDING_VIEW(user.id)")
                    .where("p.createdAt").ge(threeMonthsAgo)
                    .end();
        }
    }
}
