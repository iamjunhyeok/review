package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.CampaignLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignLinkRepository extends JpaRepository<CampaignLink, Long> {

    @Modifying
    @Query("delete from CampaignLink l where l.campaign.id = :campaignId")
    void deleteByCampaignId(Long campaignId);
}
