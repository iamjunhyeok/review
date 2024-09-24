package com.iamjunhyeok.review.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.iamjunhyeok.review.domain.QWithdrawal;
import com.iamjunhyeok.review.domain.Withdrawal;
import com.iamjunhyeok.review.projection.WithdrawalProjection;
import com.iamjunhyeok.review.projection.WithdrawalWithUserProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CustomWithdrawalRepositoryImpl implements CustomWithdrawalRepository {
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityManager entityManager;

    private final EntityViewManager entityViewManager;

    private final JPAQueryFactory qf;

    @Override
    public List<WithdrawalWithUserProjection> fetchAllWithdrawalHistory() {
        CriteriaBuilder<Withdrawal> cbf = criteriaBuilderFactory.create(entityManager, Withdrawal.class);
        return entityViewManager.applySetting(EntityViewSetting.create(WithdrawalWithUserProjection.class), cbf).getResultList();
    }

    @Override
    public List<WithdrawalProjection> fetchAllWithdrawalHistoryByUserId(Long userId, Pageable pageable) {
        QWithdrawal withdrawal = QWithdrawal.withdrawal;
        List<WithdrawalProjection> fetch = qf.select(
                        Projections.fields(
                                WithdrawalProjection.class,
                                withdrawal.id,
                                withdrawal.bank,
                                withdrawal.accountNumber,
                                withdrawal.accountHolder,
                                withdrawal.idNumber,
                                withdrawal.amount,
                                withdrawal.status,
                                withdrawal.createdAt,
                                withdrawal.updatedAt
                        )
                )
                .from(withdrawal)
                .where(withdrawal.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return fetch;
    }
}
