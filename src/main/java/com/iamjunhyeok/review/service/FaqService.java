package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Faq;
import com.iamjunhyeok.review.dto.FaqCreateRequest;
import com.iamjunhyeok.review.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    @Transactional
    public Faq create(FaqCreateRequest request) {
        return faqRepository.save(new Faq(request.getQuestion(), request.getAnswer()));
    }
}
