package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.FaqCreateRequest;
import com.iamjunhyeok.review.dto.FaqCreateResponse;
import com.iamjunhyeok.review.dto.FaqSearchResponse;
import com.iamjunhyeok.review.dto.FaqUpdateRequest;
import com.iamjunhyeok.review.dto.FaqUpdateResponse;
import com.iamjunhyeok.review.dto.FaqViewResponse;
import com.iamjunhyeok.review.service.FaqService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/support/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FaqCreateResponse> create(@RequestBody @Valid FaqCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(FaqCreateResponse.from(faqService.create(request)));
    }

    @GetMapping
    public ResponseEntity<List<FaqSearchResponse>> search(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(
                faqService.search(category)
                        .stream().map(FaqSearchResponse::from)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaqViewResponse> view(@PathVariable Long id) {
        return ResponseEntity.ok(FaqViewResponse.from(faqService.findById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<FaqUpdateResponse> update(@PathVariable Long id, @RequestBody @Valid FaqUpdateRequest request) {
        return ResponseEntity.ok(FaqUpdateResponse.from(faqService.update(id, request)));
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
