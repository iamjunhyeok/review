package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.InquiryAnswerModifyRequest;
import com.iamjunhyeok.review.dto.request.InquiryAnswerRegisterRequest;
import com.iamjunhyeok.review.service.InquiryAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/support/inquiries")
@RequiredArgsConstructor
public class InquiryAnswerController {

    private final InquiryAnswerService inquiryAnswerService;

    /**
     * 문의에 대한 답변등록
     *
     * @param inquiryId
     * @param request
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{inquiryId}/answers")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@PathVariable Long inquiryId, @RequestBody @Valid InquiryAnswerRegisterRequest request) {
        inquiryAnswerService.register(inquiryId, request);
    }

    /**
     * 문의에 등록된 답변수정
     *
     * @param inquiryId
     * @param answerId
     * @param request
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{inquiryId}/answers/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    public void modify(@PathVariable Long inquiryId, @PathVariable Long answerId, @RequestBody @Valid InquiryAnswerModifyRequest request) {
        inquiryAnswerService.modify(inquiryId, answerId, request);
    }
}
