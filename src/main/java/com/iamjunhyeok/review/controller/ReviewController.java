package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.ReviewModifyRequest;
import com.iamjunhyeok.review.dto.request.ReviewRegisterRequest;
import com.iamjunhyeok.review.dto.request.ReviewRejectRequest;
import com.iamjunhyeok.review.dto.response.ReviewModifyResponse;
import com.iamjunhyeok.review.dto.response.ReviewRegisterResponse;
import com.iamjunhyeok.review.projection.ReviewProjection;
import com.iamjunhyeok.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 캠페인 리뷰 URL 등록
     * @param campaignId
     * @param applicationId
     * @param request
     * @return
     */
    @PreAuthorize("hasPermission(#applicationId, 'application', 'ADMIN')")
    @PostMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews")
    public ResponseEntity<ReviewRegisterResponse> register(@PathVariable Long campaignId,
                                                         @PathVariable Long applicationId,
                                                         @RequestBody @Valid ReviewRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ReviewRegisterResponse.from(reviewService.register(campaignId, applicationId, request))
        );
    }

    /**
     * 캠페인 리뷰 URL 수정
     * @param campaignId
     * @param applicationId
     * @param request
     * @return
     */
    @PreAuthorize("hasPermission(#applicationId, 'application', 'ADMIN')")
    @PatchMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews")
    public ResponseEntity<ReviewModifyResponse> modify(@PathVariable Long campaignId,
                                                       @PathVariable Long applicationId,
                                                       @RequestBody @Valid ReviewModifyRequest request) {
        return ResponseEntity.ok(
                ReviewModifyResponse.from(reviewService.modify(campaignId, applicationId, request))
        );
    }

    /**
     * 캠페인에 등록된 특정 리뷰 수정요청
     * @param campaignId
     * @param applicationId
     * @param id
     * @param request
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews/{id}/modify-request")
    @ResponseStatus(HttpStatus.OK)
    public void modifyRequest(@PathVariable Long campaignId,
                              @PathVariable Long applicationId,
                              @PathVariable Long id,
                              @RequestBody @Valid ReviewRejectRequest request) {
        reviewService.modifyRequest(campaignId, applicationId, id, request);
    }

    /**
     * 캠페인에 등록된 특정 리뷰 재검토요청
     * @param campaignId
     * @param applicationId
     * @param id
     */
    @PreAuthorize("hasPermission(#applicationId, 'application', 'ADMIN')")
    @PostMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews/{id}/reconfirm-request")
    @ResponseStatus(HttpStatus.OK)
    public void reconfirmRequest(@PathVariable Long campaignId,
                                 @PathVariable Long applicationId,
                                 @PathVariable Long id) {
        reviewService.reconfirmRequest(campaignId, applicationId, id);
    }

    /**
     * 캠페인에 등록된 특정 리뷰 확인완료
     * @param campaignId
     * @param applicationId
     * @param id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews/{id}/confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirm(@PathVariable Long campaignId,
                        @PathVariable Long applicationId,
                        @PathVariable Long id) {
        reviewService.confirm(campaignId, applicationId, id);
    }

    /**
     * 캠페인 신청서에 등록된 모든 리뷰 조회
     * @param campaignId
     * @param applicationId
     * @return
     */
    @PreAuthorize("hasPermission(#applicationId, 'application', 'ADMIN')")
    @GetMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews")
    public ResponseEntity<List<ReviewProjection>> fetchAll(@PathVariable Long campaignId,
                                                           @PathVariable Long applicationId) {
        return ResponseEntity.ok(reviewService.fetchAll(campaignId, applicationId));
    }
}
