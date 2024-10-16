package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.projection.PlanProjection;
import com.iamjunhyeok.review.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ADVERTISER')")
    @GetMapping
    public ResponseEntity<List<PlanProjection>> fetchAll() {
        return ResponseEntity.ok(planService.fetchAll());
    }
}
