package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.BannerRegisterRequest;
import com.iamjunhyeok.review.projection.BannerProjection;
import com.iamjunhyeok.review.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
