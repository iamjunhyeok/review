package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.ReviewCreateRequest;
import com.iamjunhyeok.review.dto.ReviewCreateResponse;
import com.iamjunhyeok.review.dto.ReviewUpdateRequest;
import com.iamjunhyeok.review.dto.ReviewUpdateResponse;
import com.iamjunhyeok.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews")
    public ResponseEntity<ReviewCreateResponse> create(@PathVariable Long campaignId,
                                                       @PathVariable Long applicationId,
                                                       @RequestBody @Valid ReviewCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ReviewCreateResponse.from(reviewService.create(campaignId, applicationId, request)));
    }

    @PatchMapping("/campaigns/{campaignId}/applications/{applicationId}/reviews/{id}")
    public ResponseEntity<ReviewUpdateResponse> update(@PathVariable Long campaignId,
                                                       @PathVariable Long applicationId,
                                                       @PathVariable Long id,
                                                       @RequestBody @Valid ReviewUpdateRequest request) {
        return ResponseEntity.ok(ReviewUpdateResponse.from(reviewService.update(campaignId, applicationId, id, request)));
    }
}
