package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.CodeProjection;
import com.iamjunhyeok.review.projection.FaqProjection;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.iamjunhyeok.review.domain.QFaq.faq;

@RequiredArgsConstructor
public class CustomFaqRepositoryImpl implements CustomFaqRepository {

    private final JPAQueryFactory qf;

    @Override
    public List<FaqProjection> fetchAll(Long categoryId, Pageable pageable) {
        return qf.select(
                        Projections.fields(
                                FaqProjection.class,
                                faq.id,
                                faq.question,
                                faq.answer,
                                Projections.fields(
                                        CodeProjection.class,
                                        faq.categoryCode.id,
                                        faq.categoryCode.code,
                                        faq.categoryCode.value
                                ).as("categoryCode")
                        )
                )
                .from(faq)
                .where(eq(faq.categoryCode.id, categoryId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long fetchAll(Long categoryId) {
        return qf.select(faq.count())
                .from(faq)
                .where(eq(faq.categoryCode.id, categoryId))
                .fetchOne();
    }

    @Override
    public FaqProjection fetchOne(Long id) {
        return qf.select(
                        Projections.fields(
                                FaqProjection.class,
                                faq.id,
                                faq.question,
                                faq.answer,
                                Projections.fields(
                                        CodeProjection.class,
                                        faq.categoryCode.id,
                                        faq.categoryCode.code,
                                        faq.categoryCode.value
                                ).as("categoryCode")
                        )
                )
                .from(faq)
                .where(faq.id.eq(id))
                .fetchOne();
    }

    private <T> Predicate eq(SimpleExpression<T> path, T value) {
        if (value == null) {
            return null;
        }
        return path.eq(value);
    }
}
