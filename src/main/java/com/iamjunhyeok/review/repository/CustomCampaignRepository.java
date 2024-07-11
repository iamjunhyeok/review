package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.dto.CampaignSearchProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomCampaignRepository {

    List<CampaignSearchProjection> search(String type, String category, String social, String filter, Pageable pageable, String swlat, String swlng, String nelat, String nelng);

    <T> Optional<T> fetchById(Long id, Class<T> type);
}
