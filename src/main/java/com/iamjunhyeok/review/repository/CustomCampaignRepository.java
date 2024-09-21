package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.CampaignProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomCampaignRepository {

    <T> Optional<T> fetchById(Long id, Class<T> type);

    List<UserCampaignSearchProjection> fetchAuthenticatedUserCampaigns(String status);

    List<CampaignProjection> fetchAll(Long typeCodeId, Long[] categoryCodeIds, Long[] socialCodeIds, Long[] optionCodeIds, Long regionCodeId, Pageable pageable, String swlat, String swlng, String nelat, String nelng);

    CampaignProjection fetchOne(Long id);
}
