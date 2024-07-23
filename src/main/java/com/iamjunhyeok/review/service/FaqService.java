package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Faq;
import com.iamjunhyeok.review.dto.request.FaqCreateRequest;
import com.iamjunhyeok.review.dto.request.FaqUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    @Transactional
    public Faq create(FaqCreateRequest request) {
        return faqRepository.save(Faq.of(request.getCategory(), request.getQuestion(), request.getAnswer()));
    }

    public List<Faq> search(String category) {
        return faqRepository.findAll();
    }

    public Faq findById(Long id) {
        return faqRepository.findById(id)
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
