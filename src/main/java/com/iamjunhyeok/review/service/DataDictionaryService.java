package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.DataDictionary;
import com.iamjunhyeok.review.dto.request.DataDictionaryCreateRequest;
import com.iamjunhyeok.review.dto.request.DataDictionaryUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.DataDictionarySearchProjection;
import com.iamjunhyeok.review.projection.DataDictionaryViewProjection;
import com.iamjunhyeok.review.repository.DataDictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataDictionaryService {
    private final DataDictionaryRepository dataDictionaryRepository;

    public List<DataDictionarySearchProjection> search(Long parentId) {
        return dataDictionaryRepository.findAllByParentId(parentId);
    }

    public DataDictionaryViewProjection view(Long id) {
        return dataDictionaryRepository.findDataDictionaryById(id);
    }

    @Transactional
    public void create(DataDictionaryCreateRequest request) {
        DataDictionary parent = null;
        if (request.getParentId() != null) {
            parent = dataDictionaryRepository.getReferenceById(request.getParentId());
        }
        dataDictionaryRepository.save(DataDictionary.of(
                request.getName(),
                request.getValue(),
                request.getOrder(),
                parent
        ));
    }

    @Transactional
    public void update(Long id, DataDictionaryUpdateRequest request) {
        dataDictionaryRepository.findById(id)
                .orElseThrow(() -> ErrorCode.DATA_DICTIONARY_ITEM_NOT_FOUND.build())
                .update(request);
    }
}
