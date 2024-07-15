package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.dto.CampaignSearchProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomCampaignRepository {

    List<CampaignSearchProjection> fetchAll(String type, String categories, String socials, String options, Pageable pageable, String swlat, String swlng, String nelat, String nelng);

    <T> Optional<T> fetchById(Long id, Class<T> type);
}
