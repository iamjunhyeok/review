package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.dto.ApplicantSearchResponse;
import com.iamjunhyeok.review.dto.ApplicationCancelRequest;
import com.iamjunhyeok.review.dto.ApplicationViewResponse;
import com.iamjunhyeok.review.dto.CampaignApplyRequest;
import com.iamjunhyeok.review.dto.CampaignApplyResponse;
import com.iamjunhyeok.review.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/{campaignId}/applications")
    public ResponseEntity<CampaignApplyResponse> apply(@PathVariable Long campaignId, @RequestBody CampaignApplyRequest request) {
        Application application = applicationService.apply(campaignId, request);
        return ResponseEntity.created(
                UriComponentsBuilder
                        .fromPath("/campaigns/{campaignId}/applications/{id}")
                        .buildAndExpand(campaignId, application.getId())
                        .toUri()
        ).build();
    }

    @GetMapping("/{campaignId}/applications/{id}")
    public ResponseEntity<ApplicationViewResponse> view(@PathVariable Long campaignId, @PathVariable Long id) {
        return ResponseEntity.ok(ApplicationViewResponse.from(applicationService.findByIdAndCampaignId(campaignId, id)));
    }

    @DeleteMapping("/{campaignId}/applications/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long campaignId, @PathVariable Long id, @RequestBody @Valid ApplicationCancelRequest request) {
        applicationService.cancel(campaignId, id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{campaignId}/applications/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable Long campaignId, @PathVariable Long id) {
        applicationService.approve(campaignId, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{campaignId}/applications/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long campaignId, @PathVariable Long id) {
        applicationService.reject(campaignId, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{campaignId}/applications")
    public ResponseEntity<List<ApplicantSearchResponse>> searchApplicant(@PathVariable Long campaignId) {
        return ResponseEntity.ok(
                applicationService.searchApplicant(campaignId)
                        .stream().map(ApplicantSearchResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
