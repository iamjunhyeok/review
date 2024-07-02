package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CampaignRepository extends JpaRepository<Campaign, Long>, CustomCampaignRepository {

    @Query("select c from Campaign c join fetch c.links where c.id = :id")
    Optional<Campaign> findByIdWithLink(Long id);

    @Query("""
            select
                c.id,
                c.type,
                c.category,
                c.social,
                c.title,
                c.capacity,
                c.applicationStartDate,
                c.applicationEndDate,
                c.announcementDate,
                c.reviewStartDate,
                c.reviewEndDate,
                c.offering,
                c.keyword,
                c.hashtag,
                c.mission,
                c.guide,
                c.information,
                c.status,
                c.address,
                c.rest,
                c.postalCode,
                c.longitude,
                c.latitude,
                l.link
            from Campaign c
            join c.links l
            where c.id = :id
                        """)
    Optional<List<Object[]>> findBy(Long id);
}
