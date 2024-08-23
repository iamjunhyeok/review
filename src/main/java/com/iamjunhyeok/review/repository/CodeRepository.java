package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Code;
import com.iamjunhyeok.review.projection.CodeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {

    @Query("select c.id as id, c.code as code, c.value as value, c.order as order, c.deleted as deleted from Code c where c.parent.id = :parentId")
    List<CodeProjection> fetchAllByParentId(Long parentId);

    @Query("select c.id as id, c.code as code, c.value as value, c.order as order, c.deleted as deleted from Code c where c.parent.code = :parentCode")
    List<CodeProjection> fetchAllByParentCode(String parentCode);

    List<CodeProjection> findByParentIsNull();

}
