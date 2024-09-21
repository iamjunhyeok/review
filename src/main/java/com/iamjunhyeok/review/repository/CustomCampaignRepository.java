package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.projection.CampaignSearchView;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomCampaignRepository {

    List<CampaignSearchView> fetchAll(String type, String categories, String socials, String options, Long region, Pageable pageable, String swlat, String swlng, String nelat, String nelng);

    <T> Optional<T> fetchById(Long id, Class<T> type);

    List<UserCampaignSearchProjection> fetchAuthenticatedUserCampaigns(String status);

    List<CampaignSearchView> fetchAll(String type, String categories, String socials, String options, String status, Pageable pageable);

    List<CampaignSearchView> fetchAll(Long typeCodeId, Long[] categoryCodeIds, Long[] socialCodeIds, Long[] optionCodeIds, Long regionCodeId, Pageable pageable, String swlat, String swlng, String nelat, String nelng);

}
