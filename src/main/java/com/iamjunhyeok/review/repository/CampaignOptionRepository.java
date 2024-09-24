package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.CampaignOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignOptionRepository extends JpaRepository<CampaignOption, Long> {

    @Modifying
    @Query("delete from CampaignOption o where o.campaign.id = :campaignId")
    void deleteAllByCampaignId(Long campaignId);
}
