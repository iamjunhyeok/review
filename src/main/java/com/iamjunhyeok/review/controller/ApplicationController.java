package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.dto.ApplicationReadResponse;
import com.iamjunhyeok.review.dto.CampaignApplyRequest;
import com.iamjunhyeok.review.dto.CampaignApplyResponse;
import com.iamjunhyeok.review.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/{id}/apply")
    public ResponseEntity<CampaignApplyResponse> apply(@PathVariable Long id, @RequestBody CampaignApplyRequest request) {
        Application application = applicationService.apply(id, request);
        return ResponseEntity.created(
                UriComponentsBuilder
                        .fromPath("/campaigns/applications/{id}")
                        .buildAndExpand(application.getId())
                        .toUri()
        ).build();
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<ApplicationReadResponse> read(@PathVariable Long id) {
        return ResponseEntity.ok(ApplicationReadResponse.from(applicationService.findById(id)));
    }
}
