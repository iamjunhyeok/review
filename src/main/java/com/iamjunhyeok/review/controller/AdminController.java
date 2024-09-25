package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.BannerRegisterRequest;
import com.iamjunhyeok.review.dto.request.PlanRegisterRequest;
import com.iamjunhyeok.review.projection.AdvertiserProjection;
import com.iamjunhyeok.review.projection.BannerProjection;
import com.iamjunhyeok.review.projection.PlanProjection;
import com.iamjunhyeok.review.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/banners")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerBanner(@RequestPart BannerRegisterRequest request,
                               @RequestPart MultipartFile file) throws IOException {
        adminService.registerBanner(request, file);
    }

    @GetMapping("/banners")
    public ResponseEntity<List<BannerProjection>> fetchAll(Pageable pageable) {
        return ResponseEntity.ok(adminService.fetchAll(pageable));
    }

    @PostMapping("/plans")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerPlan(@RequestBody PlanRegisterRequest request) {
        adminService.registerPlan(request);
    }

    @GetMapping("/plans")
    public ResponseEntity<List<PlanProjection>> fetchAllPlans() {
        return ResponseEntity.ok(adminService.fetchAllPlans());
    }

    @PostMapping("/users/{userId}/plans/{planId}/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribe(@PathVariable Long userId, @PathVariable Long planId) {
        adminService.subscribe(userId, planId);
    }

    @GetMapping("/advertisers")
    public List<AdvertiserProjection> fetchAllAdvertisers(@RequestParam(required = false) String status) {
        return adminService.fetchAllAdvertisers(status);
    }
}
