package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.InquiryCreateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.InquiryRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    private final UserRepository userRepository;

    @Transactional
    public Inquiry create(InquiryCreateRequest request) {
        // 로그인 개발 시, 수정할 것!!
        User user = userRepository.findById(1L)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());
        return inquiryRepository.save(Inquiry.of(request.getTitle(), request.getContent(), user));
    }
}
