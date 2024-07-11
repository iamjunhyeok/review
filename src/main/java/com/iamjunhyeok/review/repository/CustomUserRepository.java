package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.dto.UserCampaignSearchProjection;
import com.iamjunhyeok.review.dto.UserSearchProjection;
import com.iamjunhyeok.review.dto.UserViewProjection;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository {

    List<UserSearchProjection> search();

    Optional<UserViewProjection> fetchById(Long id);

    List<UserCampaignSearchProjection> fetchAllByUserIdAndApplicationStatus(Long userId, ApplicationStatus status);
}
