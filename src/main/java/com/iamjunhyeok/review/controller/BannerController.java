package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.projection.BannerSimpleProjection;
import com.iamjunhyeok.review.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public List<BannerSimpleProjection> fetchAll() {
        return bannerService.fetchAll();
    }
}
