package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.dto.CampaignSearchProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomCampaignRepository {

    List<CampaignSearchProjection> search(String type, String category, String filter, Pageable pageable);
}
