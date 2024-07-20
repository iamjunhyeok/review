package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.dto.CodeProjection;
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
        return codeRepository.fetchAllByParentId(parentId);
    }

}
