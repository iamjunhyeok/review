package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.dto.InquiryCreateRequest;
import com.iamjunhyeok.review.dto.InquiryCreateResponse;
import com.iamjunhyeok.review.dto.InquirySearchRequest;
import com.iamjunhyeok.review.dto.InquirySearchResponse;
import com.iamjunhyeok.review.dto.InquiryViewResponse;
import com.iamjunhyeok.review.service.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/support/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<InquiryCreateResponse> create(@RequestBody @Valid InquiryCreateRequest request) {
        Inquiry inquiry = inquiryService.create(request);
        return ResponseEntity.created(
                UriComponentsBuilder.fromPath("/support/inquiries/{id}")
                        .buildAndExpand(inquiry.getId())
                        .toUri()
        ).build();
    }

    @GetMapping
    public ResponseEntity<List<InquirySearchResponse>> search(InquirySearchRequest request) {
        return ResponseEntity.ok(
                inquiryService.search(request)
                        .stream().map(inquiry -> InquirySearchResponse.from(inquiry))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<InquiryViewResponse> view(@PathVariable Long id) {
        return ResponseEntity.ok(InquiryViewResponse.from(inquiryService.findById(id)));
    }
}
