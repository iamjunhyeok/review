package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Faq;
import com.iamjunhyeok.review.dto.request.FaqCreateRequest;
import com.iamjunhyeok.review.dto.request.FaqUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.FaqProjection;
import com.iamjunhyeok.review.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    @Transactional
    public Faq register(FaqCreateRequest request) {
        return faqRepository.save(Faq.of(request.getCategory(), request.getQuestion(), request.getAnswer()));
    }

    public List<FaqProjection> fetchAll(String category, Pageable pageable) {
        return faqRepository.fetchAll(category, pageable);
    }

    public FaqProjection fetchOne(Long id) {
        return faqRepository.fetchOne(id)
                .orElseThrow(() -> ErrorCode.FAQ_NOT_FOUND.build());
    }

    @Transactional
    public Faq update(Long id, FaqUpdateRequest request) {
        return faqRepository.findById(id)
                .orElseThrow(() -> ErrorCode.FAQ_NOT_FOUND.build())
                .update(request.getCategory(), request.getQuestion(), request.getAnswer());
    }

    @Transactional
    public void delete(Long id) {
        faqRepository.findById(id)
                .orElseThrow(() -> ErrorCode.FAQ_NOT_FOUND.build())
                .delete();
    }
}
