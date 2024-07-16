package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.CampaignCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignCodeRepository extends JpaRepository<CampaignCode, Long> {
}