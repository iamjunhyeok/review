package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.domain.InquiryAnswer;
import com.iamjunhyeok.review.dto.InquiryAnswerModifyRequest;
import com.iamjunhyeok.review.dto.InquiryAnswerRegisterRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.InquiryAnswerRepository;
import com.iamjunhyeok.review.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryAnswerService {

    private final InquiryRepository inquiryRepository;

    private final InquiryAnswerRepository inquiryAnswerRepository;

    @Transactional
    public InquiryAnswer register(Long inquiryId, InquiryAnswerRegisterRequest request) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> ErrorCode.INQUIRY_NOT_FOUND.build());

        InquiryAnswer inquiryAnswer = InquiryAnswer.of(request.getTitle(), request.getContent());
        inquiryAnswerRepository.save(inquiryAnswer);

        inquiry.registerAnswer(inquiryAnswer);

        return inquiryAnswer;
    }

    @Transactional
    public InquiryAnswer modify(Long inquiryId, Long id, InquiryAnswerModifyRequest request) {
        return inquiryAnswerRepository.findByIdAndInquiryId(id, inquiryId)
                .orElseThrow(() -> ErrorCode.ANSWER_NOT_FOUND.build())
                .update(request.getTitle(), request.getContent());
    }
}
