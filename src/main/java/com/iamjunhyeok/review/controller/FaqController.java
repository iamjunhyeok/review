package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.Faq;
import com.iamjunhyeok.review.dto.FaqCreateRequest;
import com.iamjunhyeok.review.dto.FaqCreateResponse;
import com.iamjunhyeok.review.service.FaqService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/support/faqs")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @PostMapping
    public ResponseEntity<FaqCreateResponse> create(@RequestBody @Valid FaqCreateRequest request) {
        Faq faq = faqService.create(request);
        return ResponseEntity.created(
                UriComponentsBuilder.fromPath("/support/faqs/{id}")
                        .buildAndExpand(faq.getId())
                        .toUri()
        ).build();
    }
}
