package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.ApplicantSearchResponse;
import com.iamjunhyeok.review.dto.ApplicationCancelRequest;
import com.iamjunhyeok.review.dto.ApplicationViewResponse;
import com.iamjunhyeok.review.dto.CampaignApplyRequest;
import com.iamjunhyeok.review.dto.CampaignApplyResponse;
import com.iamjunhyeok.review.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/{campaignId}/applications")
    public ResponseEntity<CampaignApplyResponse> apply(@PathVariable Long campaignId, @RequestBody CampaignApplyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CampaignApplyResponse.from(applicationService.apply(campaignId, request)));
    }

    @GetMapping("/{campaignId}/applications/{id}")
    public ResponseEntity<ApplicationViewResponse> view(@PathVariable Long campaignId, @PathVariable Long id) {
        return ResponseEntity.ok(ApplicationViewResponse.from(applicationService.findByIdAndCampaignId(campaignId, id)));
    }

    @PatchMapping("/{campaignId}/applications/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancel(@PathVariable Long campaignId, @PathVariable Long id, @RequestBody @Valid ApplicationCancelRequest request) {
        applicationService.cancel(campaignId, id, request);
    }

    @PatchMapping("/{campaignId}/applications/{id}/approve")
    @ResponseStatus(HttpStatus.OK)
    public void approve(@PathVariable Long campaignId, @PathVariable Long id) {
        applicationService.approve(campaignId, id);
    }

    @GetMapping("/{campaignId}/applications")
    public ResponseEntity<List<ApplicantSearchResponse>> searchApplicants(@PathVariable Long campaignId) {
        return ResponseEntity.ok(
                applicationService.searchApplicants(campaignId)
                        .stream()
                        .map(ApplicantSearchResponse::from)
                        .toList()
        );
    }

}
