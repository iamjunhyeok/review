package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Notice;
import com.iamjunhyeok.review.dto.request.NoticeModifyRequest;
import com.iamjunhyeok.review.dto.request.NoticeRegisterRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.NoticeProjection;
import com.iamjunhyeok.review.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public void register(NoticeRegisterRequest request) {
        noticeRepository.save(Notice.of(request.getCategoryCode(), request.getTitle(), request.getContent()));
    }

    @Transactional
    public void modify(Long id, NoticeModifyRequest request) {
        noticeRepository.findById(id)
                .orElseThrow(() -> ErrorCode.NOTICE_NOT_FOUND.build())
                .modify(request.getCategoryCode(), request.getTitle(), request.getContent());
    }

    public List<NoticeProjection> fetchAll() {
        return noticeRepository.fetchAll();
    }

    public NoticeProjection fetchById(Long id) {
        return noticeRepository.fetchById(id)
                .orElseThrow(() -> ErrorCode.NOTICE_NOT_FOUND.build());
    }
}
