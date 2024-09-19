package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.projection.PenaltyProjection;
import com.iamjunhyeok.review.service.PenaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PenaltyController {

    private final PenaltyService penaltyService;

    /**
     * 특정 사용자의 패널티 조회
     * @param userId
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{userId}/penalties")
    public ResponseEntity<List<PenaltyProjection>> fetchAll(@PathVariable Long userId) {
        return ResponseEntity.ok(penaltyService.fetchAll(userId));
    }

    /**
     * 패널티 내역 삭제
     * @param userId
     * @param id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{userId}/penalties/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId, @PathVariable Long id) {
        penaltyService.delete(userId, id);
    }
}
