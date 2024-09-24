package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.projection.PointProjection;
import com.iamjunhyeok.review.projection.WithdrawalWithUserProjection;
import com.iamjunhyeok.review.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    /**
     * 특정 사용자의 포인트 전체내역 조회
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}/points")
    public ResponseEntity<List<PointProjection>> fetchAll(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(pointService.fetchAllPointsHistoryByUserId(id, pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/withdrawals")
    public ResponseEntity<List<WithdrawalWithUserProjection>> fetchAllWithdrawalHistory() {
        return ResponseEntity.ok(pointService.fetchAllWithdrawalHistory());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/withdrawals/{withdrawalId}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeWithdrawal(@PathVariable Long withdrawalId) {
        pointService.completeWithdrawal(withdrawalId);
    }
}
