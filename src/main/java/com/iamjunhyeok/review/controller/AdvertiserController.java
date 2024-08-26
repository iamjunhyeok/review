package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.dto.request.AdvertiserApplyRequest;
import com.iamjunhyeok.review.service.AdvertiserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/advertiser")
@RequiredArgsConstructor
public class AdvertiserController {

    private final AdvertiserService advertiserService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/applications")
    @ResponseStatus(HttpStatus.CREATED)
    public void applyAdvertiser(@RequestPart AdvertiserApplyRequest request,
                                @RequestPart MultipartFile file,
                                @AuthenticationPrincipal CustomOAuth2User principal) throws IOException {
        advertiserService.apply(request, file, principal);
    }
}
