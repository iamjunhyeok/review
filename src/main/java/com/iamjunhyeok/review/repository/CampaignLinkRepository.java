package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.CampaignLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignLinkRepository extends JpaRepository<CampaignLink, Long> {
}
