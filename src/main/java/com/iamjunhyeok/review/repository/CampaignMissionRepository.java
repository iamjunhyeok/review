package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.CampaignMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignMissionRepository extends JpaRepository<CampaignMission, Long> {

    @Modifying
    @Query("delete from CampaignMission m where m.campaign.id = :campaignId")
    void deleteAllByCampaignId(Long campaignId);
}
