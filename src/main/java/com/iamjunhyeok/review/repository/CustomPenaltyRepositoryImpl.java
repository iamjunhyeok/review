package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.QPenalty;
import com.iamjunhyeok.review.projection.PenaltyProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CustomPenaltyRepositoryImpl implements CustomPenaltyRepository {

    private final JPAQueryFactory qf;

    @Override
    public List<PenaltyProjection> fetchAllPenaltyHistoryByUserId(Long userId, Pageable pageable) {
        QPenalty penalty = QPenalty.penalty;
        List<PenaltyProjection> fetch = qf.select(
                        Projections.fields(
                                PenaltyProjection.class,
                                penalty.id,
                                penalty.reason,
                                penalty.details,
                                penalty.point
                        )
                ).from(penalty)
                .innerJoin(penalty.application)
                .where(penalty.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return fetch;
    }
}
