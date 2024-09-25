package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.BannerProjection;
import com.iamjunhyeok.review.projection.CodeProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static com.iamjunhyeok.review.domain.QBanner.banner;

@RequiredArgsConstructor
public class CustomBannerRepositoryImpl implements CustomBannerRepository {

    private final JPAQueryFactory qf;

    @Override
    public List<BannerProjection> fetchAll(Pageable pageable) {
        return qf.select(
                        Projections.fields(
                                BannerProjection.class,
                                banner.id,
                                banner.imageName,
                                banner.linkUrl,
                                banner.startDate,
                                banner.endDate,
                                banner.order,
                                banner.deleted,
                                Projections.fields(
                                        CodeProjection.class,
                                        banner.screen.id,
                                        banner.screen.code,
                                        banner.screen.value
                                ).as("screenCode")
                        )
                )
                .from(banner)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<BannerProjection> fetchAllValidBanners() {
        LocalDate today = LocalDate.now();
        return qf.select(
                        Projections.fields(
                                BannerProjection.class,
                                banner.id,
                                banner.imageName,
                                banner.linkUrl
                        )
                )
                .from(banner)
                .where(
                        banner.startDate.loe(today)
                                .and(banner.endDate.goe(today))
                                .and(banner.deleted.isFalse())
                )
                .fetch();
    }
}
