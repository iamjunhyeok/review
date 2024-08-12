package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.dto.request.ApplicationCancelRequest;
import com.iamjunhyeok.review.dto.request.CampaignApplyRequest;
import com.iamjunhyeok.review.dto.response.CampaignApplyResponse;
import com.iamjunhyeok.review.projection.ApplicantProjection;
import com.iamjunhyeok.review.projection.ApplicationProjection;
import com.iamjunhyeok.review.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * 인증된 일반 사용자는 등록된 캠페인에 신청 가능
     * @param campaignId
     * @param request
     * @param principal
     * @return
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{campaignId}/apply")
    public ResponseEntity<CampaignApplyResponse> apply(@PathVariable Long campaignId,
                                                       @RequestBody CampaignApplyRequest request,
                                                       @AuthenticationPrincipal CustomOAuth2User principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CampaignApplyResponse.from(applicationService.apply(campaignId, request, principal.getUserId())));
    }

    @GetMapping("/{campaignId}/applied")
    public Boolean checkApplied(@PathVariable Long campaignId, @AuthenticationPrincipal CustomOAuth2User principal) {
        return applicationService.checkApplied(campaignId, principal);
    }

    /**
     * 자신이 캠페인 신청 내용만 조회 가능
     * @param campaignId
     * @param applicationId
     * @return
     */
    @PreAuthorize("hasPermission(#applicationId, 'application', 'ADMIN')")
    @GetMapping("/{campaignId}/applications/{applicationId}")
    public ResponseEntity<ApplicationProjection> fetchOne(@PathVariable Long campaignId, @PathVariable Long applicationId) {
        return ResponseEntity.ok(applicationService.fetchOne(campaignId, applicationId));
    }

    /**
     * 자신이 신청한 캠페인 취소
     * @param campaignId
     * @param applicationId
     * @param request
     * @param files
     * @param principal
     * @throws IOException
     */
    @PreAuthorize("hasPermission(#applicationId, 'application', 'ADMIN')")
    @PatchMapping("/{campaignId}/applications/{applicationId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancel(@PathVariable Long campaignId,
                       @PathVariable Long applicationId,
                       @RequestPart @Valid ApplicationCancelRequest request,
                       @RequestPart(required = false) List<MultipartFile> files,
                       @AuthenticationPrincipal CustomOAuth2User principal) throws IOException {
        applicationService.cancel(campaignId, applicationId, request, files, principal.getUserId());
    }

    /**
     * 관리자는 신청자를 승인
     * @param campaignId
     * @param applicationId
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{campaignId}/applications/{applicationId}/approve")
    @ResponseStatus(HttpStatus.OK)
    public void approve(@PathVariable Long campaignId, @PathVariable Long applicationId) {
        applicationService.approve(campaignId, applicationId);
    }

    /**
     * 캠페인 신청자 조회
     * @param campaignId
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{campaignId}/applications")
    public ResponseEntity<List<ApplicantProjection>> fetchAllApplicants(@PathVariable Long campaignId) {
        return ResponseEntity.ok(applicationService.fetchAllApplicants(campaignId));
    }

    @PreAuthorize("hasPermission(#applicationId, 'application', 'ADMIN')")
    @DeleteMapping("/{campaignId}/applications/{applicationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long campaignId, @PathVariable Long applicationId) {
        applicationService.delete(campaignId, applicationId);
    }
}
