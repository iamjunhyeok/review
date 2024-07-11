package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.dto.UserSearchProjection;
import com.iamjunhyeok.review.dto.UserViewProjection;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository {

    List<UserSearchProjection> search();

    Optional<UserViewProjection> fetchById(Long id);
}
