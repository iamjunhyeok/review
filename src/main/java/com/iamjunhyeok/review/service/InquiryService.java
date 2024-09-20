package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.request.InquiryCreateRequest;
import com.iamjunhyeok.review.dto.request.InquiryUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.InquiryProjection;
import com.iamjunhyeok.review.projection.InquiryWithAnswerAndUserProjection;
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

    @Transactional
    public Inquiry register(InquiryCreateRequest request, Long userId) {
        User user = userRepository.getReferenceById(userId);
        return inquiryRepository.save(Inquiry.of(request.getCategory(), request.getTitle(), request.getContent(), user));
    }

    @Transactional
    public Inquiry modify(Long id, InquiryUpdateRequest request) {
        return inquiryRepository.findById(id)
                .orElseThrow(() -> ErrorCode.INQUIRY_NOT_FOUND.build())
                .update(request.getCategory(), request.getTitle(), request.getContent());
    }

    @Transactional
    public void delete(Long id) {
        inquiryRepository.findById(id)
                .orElseThrow(() -> ErrorCode.INQUIRY_NOT_FOUND.build())
                .delete();
    }

    public List<InquiryWithAnswerAndUserProjection> fetchAllInquiriesForAuthenticatedUser(String category) {
        return inquiryRepository.fetchAllInquiriesForAuthenticatedUser(category);
    }

    public List<InquiryProjection> fetchAllInquiries(Pageable pageable) {
        return inquiryRepository.fetchAllInquiries(pageable);
    }

    public InquiryWithAnswerAndUserProjection fetchOne(Long id) {
        return inquiryRepository.fetchOne(id)
                .orElseThrow(() -> ErrorCode.INQUIRY_NOT_FOUND.build());
    }

    public List<InquiryProjection> fetchAllInquiriesByUserId(Long userId, Pageable pageable) {
        return inquiryRepository.fetchAllInquiriesByUserId(userId, pageable);
    }
}
