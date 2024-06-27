package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.dto.CampaignSearchProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("select c.type as type, c.social as social, c.title as title, c.capacity as capacity, count(a) as applicantsCount from Campaign c left join c.applications a group by c.id")
    List<CampaignSearchProjection> search(String query);

    @Query("select c from Campaign c join fetch c.links where c.id = :id")
    Optional<Campaign> findByIdWithLink(Long id);

}
