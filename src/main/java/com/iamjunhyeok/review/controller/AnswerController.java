package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.AnswerCreateRequest;
import com.iamjunhyeok.review.dto.AnswerCreateResponse;
import com.iamjunhyeok.review.dto.AnswerUpdateRequest;
import com.iamjunhyeok.review.dto.AnswerUpdateResponse;
import com.iamjunhyeok.review.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/support/inquiries")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/{inquiryId}/answers")
    public ResponseEntity<AnswerCreateResponse> create(@PathVariable Long inquiryId, @RequestBody @Valid AnswerCreateRequest request) {
        answerService.create(inquiryId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{inquiryId}/answers/{id}")
    public ResponseEntity<AnswerUpdateResponse> update(@PathVariable Long inquiryId, @PathVariable Long id, @RequestBody @Valid AnswerUpdateRequest request) {
        answerService.update(inquiryId, id, request);
        return ResponseEntity.ok().build();
    }
}
