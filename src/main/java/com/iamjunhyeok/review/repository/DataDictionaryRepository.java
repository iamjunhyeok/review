package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.DataDictionary;
import com.iamjunhyeok.review.projection.DataDictionarySearchProjection;
import com.iamjunhyeok.review.projection.DataDictionaryViewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDictionaryRepository extends JpaRepository<DataDictionary, Long> {
    List<DataDictionarySearchProjection> findAllByParentId(Long parentId);

    DataDictionaryViewProjection findDataDictionaryById(Long id);
}
