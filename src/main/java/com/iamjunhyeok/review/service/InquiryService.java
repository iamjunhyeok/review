package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.InquiryCreateRequest;
import com.iamjunhyeok.review.dto.InquirySearchRequest;
import com.iamjunhyeok.review.dto.InquiryUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.InquiryRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    private final UserRepository userRepository;

    @Transactional
    public Inquiry create(InquiryCreateRequest request) {
        // 로그인 개발 시, 수정할 것!!
//        User user = userRepository.findById(1L)
//                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());
        User user = userRepository.getReferenceById(1L);
        return inquiryRepository.save(Inquiry.of(request.getCategory(), request.getTitle(), request.getContent(), user));
    }

    public List<Inquiry> search(InquirySearchRequest request) {
        return inquiryRepository.findAll();
    }

    public Inquiry findById(Long id) {
        return inquiryRepository.findById(id)
                .orElseThrow(() -> ErrorCode.INQUIRY_NOT_FOUND.build());
    }

    @Transactional
    public Inquiry update(Long id, InquiryUpdateRequest request) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> ErrorCode.INQUIRY_NOT_FOUND.build());
        return inquiry.update(request.getCategory(), request.getTitle(), request.getContent());
    }

    @Transactional
    public void delete(Long id) {
        inquiryRepository.findById(id)
                .ifPresentOrElse(inquiry -> inquiry.delete(), () -> {
                    throw ErrorCode.INQUIRY_NOT_FOUND.build();
                });
    }
}
