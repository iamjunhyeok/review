package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.projection.PointProjection;
import com.iamjunhyeok.review.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    /**
     * 인증된 사용자의 포인트 전체내역 조회
     * @param user
     * @return
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/me/points")
    public ResponseEntity<List<PointProjection>> fetchAllPointsForAuthenticatedUser(@AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(pointService.getPoints(user.getUserId()));
    }

    /**
     * 특정 사용자의 포인트 전체내역 조회
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}/points")
    public ResponseEntity<List<PointProjection>> fetchAll(@PathVariable Long id) {
        return ResponseEntity.ok(pointService.getPoints(id));
    }
}
