package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Code;
import com.iamjunhyeok.review.domain.Notice;
import com.iamjunhyeok.review.dto.request.NoticeModifyRequest;
import com.iamjunhyeok.review.dto.request.NoticeRegisterRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.NoticeProjection;
import com.iamjunhyeok.review.repository.CodeRepository;
import com.iamjunhyeok.review.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final CodeRepository codeRepository;

    @Transactional
    public void register(NoticeRegisterRequest request) {
        Code category = codeRepository.getReferenceById(request.getCategoryCodeId());
        noticeRepository.save(Notice.of(category, request.getTitle(), request.getContent()));
    }

    @Transactional
    public void modify(Long id, NoticeModifyRequest request) {
        Code category = codeRepository.getReferenceById(request.getCategoryCodeId());
        noticeRepository.findById(id)
                .orElseThrow(() -> ErrorCode.NOTICE_NOT_FOUND.build())
                .modify(category, request.getTitle(), request.getContent());
    }

    public List<NoticeProjection> fetchAll(Long categoryCodeId, Pageable pageable) {
        return noticeRepository.fetchAll(categoryCodeId, pageable);
    }

    public NoticeProjection fetchOne(Long id) {
        return noticeRepository.fetchOne(id);
    }
}
