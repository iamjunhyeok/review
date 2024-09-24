package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.FaqCreateRequest;
import com.iamjunhyeok.review.dto.request.FaqUpdateRequest;
import com.iamjunhyeok.review.projection.FaqProjection;
import com.iamjunhyeok.review.service.FaqService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/support/faqs")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    /**
     * FAQ 등록
     *
     * @param request
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid FaqCreateRequest request) {
        faqService.register(request);
    }

    /**
     * 전체 FAQ 조회
     * @param category
     * @return
     */
    @GetMapping
    public ResponseEntity<List<FaqProjection>> fetchAll(@RequestParam(value = "category", required = false) Long categoryId, Pageable pageable) {
        return ResponseEntity.ok(faqService.fetchAll(categoryId, pageable));
    }

    @GetMapping("/count")
    public Long fetchAll(@RequestParam(value = "category", required = false) Long categoryId) {
        return faqService.fetchAll(categoryId);
    }

    /**
     * 개별 FAQ 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<FaqProjection> fetchOne(@PathVariable Long id) {
        return ResponseEntity.ok(faqService.fetchOne(id));
    }

    /**
     * FAQ 수정
     *
     * @param id
     * @param request
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void modify(@PathVariable Long id, @RequestBody @Valid FaqUpdateRequest request) {
        faqService.update(id, request);
    }

    /**
     * FAQ 삭제
     * @param id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        faqService.delete(id);
    }
}
