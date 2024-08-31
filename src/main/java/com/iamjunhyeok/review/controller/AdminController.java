package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.BannerRegisterRequest;
import com.iamjunhyeok.review.dto.request.PlanRegisterRequest;
import com.iamjunhyeok.review.projection.AdvertiserProjection;
import com.iamjunhyeok.review.projection.BannerProjection;
import com.iamjunhyeok.review.projection.CampaignSearchProjection;
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

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/banners/{codeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerBanner(@PathVariable Long codeId,
                       @RequestPart BannerRegisterRequest request,
                       @RequestPart MultipartFile file) throws IOException {
        adminService.registerBanner(codeId, request, file);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/banners")
    public ResponseEntity<List<BannerProjection>> fetchAll() {
        return ResponseEntity.ok(adminService.fetchAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/plans")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerPlan(@RequestBody PlanRegisterRequest request) {
        adminService.registerPlan(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plans")
    public ResponseEntity<List<PlanProjection>> fetchAllPlans() {
        return ResponseEntity.ok(adminService.fetchAllPlans());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/campaigns")
    public ResponseEntity<List<CampaignSearchProjection>> fetchAllCampaigns(@RequestParam(required = false) String type,
                                                                 @RequestParam(required = false) String categories,
                                                                 @RequestParam(required = false) String socials,
                                                                 @RequestParam(required = false) String options,
                                                                 @RequestParam(required = false) String status,
                                                                 Pageable pageable
    ) {
        return ResponseEntity.ok(adminService.fetchAllCampaigns(type, categories, socials, options, status, pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/{userId}/plans/{planId}/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribe(@PathVariable Long userId, @PathVariable Long planId) {
        adminService.subscribe(userId, planId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/advertisers")
    public List<AdvertiserProjection> fetchAllAdvertisers(@RequestParam(required = false) String status) {
        return adminService.fetchAllAdvertisers(status);
    }
}
