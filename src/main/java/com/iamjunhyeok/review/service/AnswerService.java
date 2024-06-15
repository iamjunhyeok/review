package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Answer;
import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.dto.AnswerCreateRequest;
import com.iamjunhyeok.review.dto.AnswerUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.AnswerRepository;
import com.iamjunhyeok.review.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerService {

    private final InquiryRepository inquiryRepository;

    private final AnswerRepository answerRepository;

    @Transactional
    public Answer create(Long inquiryId, AnswerCreateRequest request) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> ErrorCode.INQUIRY_NOT_FOUND.build());
        Answer answer = answerRepository.save(Answer.of(request.getTitle(), request.getContent()));
        inquiry.registerAnswer(answer);
        return answer;
    }

    @Transactional
    public void update(Long inquiryId, Long id, AnswerUpdateRequest request) {
        Inquiry inquiry = inquiryRepository.findByIdAndAnswerId(inquiryId, id)
                .orElseThrow(() -> ErrorCode.ANSWER_NOT_FOUND.build());

        // 성능 최적화를 위해 수정해야 함!!!
        inquiry.getAnswer().update(request.getTitle(), request.getContent());
    }
}
