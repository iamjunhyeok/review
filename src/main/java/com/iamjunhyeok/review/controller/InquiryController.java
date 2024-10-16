package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.dto.request.InquiryCreateRequest;
import com.iamjunhyeok.review.dto.request.InquiryUpdateRequest;
import com.iamjunhyeok.review.projection.InquiryProjection;
import com.iamjunhyeok.review.service.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/support/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    /**
     * 관리자에게 1:1 문의하기를 등록
     * 인증된 사용자만이 문의하기 등록 가능
     *
     * @param request
     * @param principal
     * @return
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid InquiryCreateRequest request,
                         @AuthenticationPrincipal CustomOAuth2User principal) {
        inquiryService.register(request, principal.getUserId());
    }

    /**
     * 1:1 문의하기 내용을 수정
     * 작성자 또는 관리자만이 내용 수정 가능
     * 내부적으로 답변이 완료된 문의는 수정 불가 처리
     * @param id
     * @param request
     * @return
     */
    @PreAuthorize("hasPermission(#id, 'inquiry', 'ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void modify(@PathVariable Long id, @RequestBody @Valid InquiryUpdateRequest request) {
        inquiryService.modify(id, request);
    }

    /**
     * 등록된 1:1 문의하기를 삭제
     * 작성자 또는 관리자만이 삭제 가능
     * 내부적으로 답변이 완료된 문의는 삭제 불가 처리
     * @param id
     */
    @PreAuthorize("hasPermission(#id, 'inquiry', 'ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inquiryService.delete(id);
    }

    /**
     * 사용자가 등록한 모든 1:1 문의 목록 조회
     * 관리자 권한만이 모든 문의 목록 조회 가능
     * @param category
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<InquiryProjection>> fetchAllInquiries(@RequestParam(value = "category", required = false) Long categoryCodeId,
                                                                     Pageable pageable) {
        return ResponseEntity.ok(inquiryService.fetchAll(categoryCodeId, pageable));
    }

    /**
     * 1:1 문의 내용 조회
     * @param id
     * @return
     */
    @PreAuthorize("hasPermission(#id, 'inquiry', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<InquiryProjection> fetchOne(@PathVariable Long id) {
        return ResponseEntity.ok(inquiryService.fetchOne(id));
    }
}
