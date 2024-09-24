package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.MappingSubquery;
import com.blazebit.persistence.view.SubqueryProvider;
import com.iamjunhyeok.review.domain.Penalty;
import com.iamjunhyeok.review.domain.Point;
import com.iamjunhyeok.review.domain.User;

import java.time.LocalDate;

@EntityView(User.class)
public interface UserSummaryProjection {
    @IdMapping
    Long getId();

    String getNickname();

    LocalDate getBirthDate();

    String getProfileImageName();

    @MappingSubquery(PointsSubqueryProvider.class)
    Long getPoints();

    @MappingSubquery(PenaltyPointsSubqueryProvider.class)
    Long getPenaltyPoints();

    class PointsSubqueryProvider implements SubqueryProvider {
        @Override
        public <T> T createSubquery(SubqueryInitiator<T> subqueryInitiator) {
            return subqueryInitiator.from(Point.class, "p")
                    .select("COALESCE(SUM(p.amount), 0)")
                    .where("p.user.id").eqExpression("EMBEDDING_VIEW(id)")
                    .end();
        }
    }

    class PenaltyPointsSubqueryProvider implements SubqueryProvider {
        @Override
        public <T> T createSubquery(SubqueryInitiator<T> subqueryInitiator) {
            return subqueryInitiator.from(Penalty.class, "p")
                    .select("COALESCE(SUM(p.point), 0)")
                    .where("p.user.id").eqExpression("EMBEDDING_VIEW(id)")
                    .end();
        }
    }
}
