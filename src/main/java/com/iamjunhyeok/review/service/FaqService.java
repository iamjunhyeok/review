package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Faq;
import com.iamjunhyeok.review.dto.FaqCreateRequest;
import com.iamjunhyeok.review.dto.FaqSearchRequest;
import com.iamjunhyeok.review.dto.FaqUpdateRequest;
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
        return faqRepository.save(new Faq(request.getQuestion(), request.getAnswer()));
    }

    public List<Faq> search(FaqSearchRequest request) {
        return faqRepository.findAll();
    }

    public Faq findById(Long id) {
        return faqRepository.findById(id)
                .orElseThrow(() -> ErrorCode.FAQ_NOT_FOUND.build());
    }

    @Transactional
    public void update(Long id, FaqUpdateRequest request) {
        Faq faq = faqRepository.findById(id)
                .orElseThrow(() -> ErrorCode.FAQ_NOT_FOUND.build());
        faq.update(request.getQuestion(), request.getAnswer());
    }

    @Transactional
    public void delete(Long id) {
        Faq faq = faqRepository.findById(id)
                .orElseThrow(() -> ErrorCode.FAQ_NOT_FOUND.build());
        faq.delete();
    }
}
