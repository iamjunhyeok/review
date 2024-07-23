package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.projection.CampaignSearchProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomCampaignRepository {

    List<CampaignSearchProjection> fetchAll(String type, String categories, String socials, String options, Pageable pageable, String swlat, String swlng, String nelat, String nelng);

    <T> Optional<T> fetchById(Long id, Class<T> type);

    List<UserCampaignSearchProjection> fetchAuthenticatedUserCampaigns(ApplicationStatus status);
}
