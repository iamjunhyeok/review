package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.ReviewCreateRequest;
import com.iamjunhyeok.review.dto.request.ReviewRejectRequest;
import com.iamjunhyeok.review.dto.request.ReviewUpdateRequest;
import com.iamjunhyeok.review.dto.response.ReviewCreateResponse;
import com.iamjunhyeok.review.dto.response.ReviewRejectResponse;
import com.iamjunhyeok.review.dto.response.ReviewUpdateResponse;
import com.iamjunhyeok.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews")
    public ResponseEntity<ReviewCreateResponse> create(@PathVariable Long campaignId,
                                                       @PathVariable Long applicationId,
                                                       @RequestBody @Valid ReviewCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ReviewCreateResponse.from(reviewService.create(campaignId, applicationId, request))
        );
    }

    @PatchMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews")
    public ResponseEntity<ReviewUpdateResponse> update(@PathVariable Long campaignId,
                                                       @PathVariable Long applicationId,
                                                       @RequestBody @Valid ReviewUpdateRequest request) {
        return ResponseEntity.ok(
                ReviewUpdateResponse.from(reviewService.update(campaignId, applicationId, request))
        );
    }

    @PatchMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews/{id}/modify-request")
    public ResponseEntity<ReviewRejectResponse> modifyRequest(@PathVariable Long campaignId,
                                                              @PathVariable Long applicationId,
                                                              @PathVariable Long id,
                                                              @RequestBody @Valid ReviewRejectRequest request) {
        return ResponseEntity.ok(ReviewRejectResponse.from(reviewService.modifyRequest(campaignId, applicationId, id, request)));
    }

    @PatchMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews/{id}/reconfirm-request")
    @ResponseStatus(HttpStatus.OK)
    public void reconfirmRequest(@PathVariable Long campaignId, @PathVariable Long applicationId, @PathVariable Long id) {
        reviewService.reconfirmRequest(campaignId, applicationId, id);
    }

    @PatchMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews/{id}/confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirm(@PathVariable Long campaignId, @PathVariable Long applicationId, @PathVariable Long id) {
        reviewService.confirm(campaignId, applicationId, id);
    }
}
