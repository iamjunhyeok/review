package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.dto.request.PointWithdrawalRequest;
import com.iamjunhyeok.review.projection.PointProjection;
import com.iamjunhyeok.review.projection.WithdrawalProjection;
import com.iamjunhyeok.review.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    /**
     * 인증된 사용자의 현재 포인트 조회
     * @param user
     * @return
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/me/point")
    public int fetchCurrentPointForAuthenticatedUser(@AuthenticationPrincipal CustomOAuth2User user) {
        return pointService.getCurrentPoints(user.getUserId());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/users/me/points/withdrawals")
    @ResponseStatus(HttpStatus.CREATED)
    public void requestWithdrawal(@RequestBody PointWithdrawalRequest request, @AuthenticationPrincipal CustomOAuth2User user) {
        pointService.withdrawPoint(request, user.getUserId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/me/points/withdrawals")
    public ResponseEntity<List<WithdrawalProjection>> fetchAllWithdrawalHistory(@AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(pointService.fetchAllWithdrawalHistory(user.getUserId()));
    }
}
