package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Code;
import com.iamjunhyeok.review.domain.Faq;
import com.iamjunhyeok.review.dto.request.FaqCreateRequest;
import com.iamjunhyeok.review.dto.request.FaqUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.FaqProjection;
import com.iamjunhyeok.review.repository.CodeRepository;
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

    private final CodeRepository codeRepository;

    @Transactional
    public Faq register(FaqCreateRequest request) {
        Code categoryCode = codeRepository.getReferenceById(request.getCategoryCodeId());
        return faqRepository.save(Faq.of(categoryCode, request.getQuestion(), request.getAnswer()));
    }

    public List<FaqProjection> fetchAll(Long categoryId, Pageable pageable) {
        return faqRepository.fetchAll(categoryId, pageable);
    }

    public Long fetchAll(Long categoryId) {
        return faqRepository.fetchAll(categoryId);
    }

    public FaqProjection fetchOne(Long id) {
        return faqRepository.fetchOne(id);
    }

    @Transactional
    public Faq update(Long id, FaqUpdateRequest request) {
        Code categoryCode = codeRepository.getReferenceById(request.getCategoryCodeId());
        return faqRepository.findById(id)
                .orElseThrow(() -> ErrorCode.FAQ_NOT_FOUND.build())
                .update(categoryCode, request.getQuestion(), request.getAnswer());
    }

    @Transactional
    public void delete(Long id) {
        faqRepository.findById(id)
                .orElseThrow(() -> ErrorCode.FAQ_NOT_FOUND.build())
                .delete();
    }
}
