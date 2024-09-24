package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.domain.InquiryAnswer;
import com.iamjunhyeok.review.dto.request.InquiryAnswerModifyRequest;
import com.iamjunhyeok.review.dto.request.InquiryAnswerRegisterRequest;
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
    public void register(Long inquiryId, InquiryAnswerRegisterRequest request) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> ErrorCode.INQUIRY_NOT_FOUND.build());

        InquiryAnswer inquiryAnswer = InquiryAnswer.of(request.getTitle(), request.getContent());
        inquiryAnswerRepository.save(inquiryAnswer);

        inquiry.registerAnswer(inquiryAnswer);
    }

    @Transactional
    public void modify(Long inquiryId, Long id, InquiryAnswerModifyRequest request) {
        inquiryAnswerRepository.findByIdAndInquiryId(id, inquiryId)
                .orElseThrow(() -> ErrorCode.ANSWER_NOT_FOUND.build())
                .update(request.getTitle(), request.getContent());
    }
}
