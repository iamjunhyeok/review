package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.projection.PointSearchProjection;
import com.iamjunhyeok.review.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @GetMapping("/users/me/points")
    public ResponseEntity<List<PointSearchProjection>> myPoints(@AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(pointService.getPoints(user.getUserId()));
    }

    @GetMapping("/users/{id}/points")
    public ResponseEntity<List<PointSearchProjection>> userPoints(@PathVariable Long id) {
        return ResponseEntity.ok(pointService.getPoints(id));
    }
}
