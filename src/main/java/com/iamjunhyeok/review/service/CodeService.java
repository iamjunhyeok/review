package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Code;
import com.iamjunhyeok.review.dto.request.CodeCreateRequest;
import com.iamjunhyeok.review.dto.request.CodeUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.CodeProjection;
import com.iamjunhyeok.review.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;

    public List<CodeProjection> fetchAllByParentId(Long parentId) {
        if (parentId == null) {
            return codeRepository.findByParentIsNull();
        }
        return codeRepository.fetchAllByParentId(parentId);
    }

    @Transactional
    public Long create(CodeCreateRequest request) {
        Code parent = codeRepository.getReferenceById(request.getParentId());
        Code saved = codeRepository.save(Code.of(request.getCode(), request.getValue(), request.getOrder(), parent));
        return saved.getId();
    }

    @Transactional
    public void update(Long id, CodeUpdateRequest request) {
        codeRepository.findById(id)
                .orElseThrow(() -> ErrorCode.CODE_NOT_FOUND.build())
                .update(request.getValue(), request.getOrder());
    }

    @Transactional
    public void delete(Long id) {
        codeRepository.findById(id)
                .orElseThrow(() -> ErrorCode.CODE_NOT_FOUND.build())
                .delete();
    }

    @Transactional
    public void restore(Long id) {
        codeRepository.findById(id)
                .orElseThrow(() -> ErrorCode.CODE_NOT_FOUND.build())
                .restore();
    }
}
