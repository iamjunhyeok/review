package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CampaignRepository extends JpaRepository<Campaign, Long>, CustomCampaignRepository {

    @Query("select c from Campaign c join fetch c.links where c.id = :id")
    Optional<Campaign> findByIdWithLink(Long id);
}
