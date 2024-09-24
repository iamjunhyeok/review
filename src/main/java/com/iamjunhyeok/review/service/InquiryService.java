package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Code;
import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.request.InquiryCreateRequest;
import com.iamjunhyeok.review.dto.request.InquiryUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.InquiryProjection;
import com.iamjunhyeok.review.repository.CodeRepository;
import com.iamjunhyeok.review.repository.InquiryRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    private final UserRepository userRepository;

    private final CodeRepository codeRepository;

    @Transactional
    public Long register(InquiryCreateRequest request, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Code category = codeRepository.getReferenceById(request.getCategoryCodeId());
        Inquiry saved = inquiryRepository.save(Inquiry.of(category, request.getTitle(), request.getContent(), user));
        return saved.getId();
    }

    @Transactional
    public void modify(Long id, InquiryUpdateRequest request) {
        Code category = codeRepository.getReferenceById(request.getCategoryCodeId());
        inquiryRepository.findById(id)
                .orElseThrow(() -> ErrorCode.INQUIRY_NOT_FOUND.build())
                .update(category, request.getTitle(), request.getContent());
    }

    @Transactional
    public void delete(Long id) {
        inquiryRepository.findById(id)
                .orElseThrow(() -> ErrorCode.INQUIRY_NOT_FOUND.build())
                .delete();
    }

    public List<InquiryProjection> fetchAll(Long categoryCodeId, Pageable pageable) {
        return inquiryRepository.fetchAll(categoryCodeId, pageable);
    }

    public InquiryProjection fetchOne(Long id) {
        return inquiryRepository.fetchOne(id);
    }

    public List<InquiryProjection> fetchAllInquiriesByUserId(Long userId, Pageable pageable) {
        return inquiryRepository.fetchAllInquiriesByUserId(userId, pageable);
    }
}
