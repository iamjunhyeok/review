package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.dto.UserSearchProjection;

import java.util.List;

public interface CustomUserRepository {

    List<UserSearchProjection> search();
}
