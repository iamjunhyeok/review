package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findAllByCodeIn(List<String> codes);
}
