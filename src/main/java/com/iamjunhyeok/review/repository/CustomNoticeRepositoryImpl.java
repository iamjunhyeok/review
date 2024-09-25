package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.CodeProjection;
import com.iamjunhyeok.review.projection.NoticeProjection;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.iamjunhyeok.review.domain.QNotice.notice;

@RequiredArgsConstructor
public class CustomNoticeRepositoryImpl implements CustomNoticeRepository {

    private final JPAQueryFactory qf;

    @Override
    public List<NoticeProjection> fetchAll(Long categoryCodeId, Pageable pageable) {
        return qf.select(
                        Projections.fields(
                                NoticeProjection.class,
                                notice.id,
                                notice.title,
                                notice.content,
                                Projections.fields(
                                        CodeProjection.class,
                                        notice.categoryCode.id,
                                        notice.categoryCode.code,
                                        notice.categoryCode.value
                                ).as("categoryCode")
                        )
                )
                .from(notice)
                .where(eq(notice.categoryCode.id, categoryCodeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public NoticeProjection fetchOne(Long id) {
        return qf.select(
                        Projections.fields(
                                NoticeProjection.class,
                                notice.id,
                                notice.title,
                                notice.content,
                                Projections.fields(
                                        CodeProjection.class,
                                        notice.categoryCode.id,
                                        notice.categoryCode.code,
                                        notice.categoryCode.value
                                ).as("categoryCode")
                        )
                )
                .from(notice)
                .where(notice.id.eq(id))
                .fetchOne();
    }

    private <T> Predicate eq(SimpleExpression<T> path, T value) {
        if (value == null) {
            return null;
        }
        return path.eq(value);
    }
}
