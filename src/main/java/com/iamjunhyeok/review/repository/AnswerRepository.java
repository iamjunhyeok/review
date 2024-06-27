package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByIdAndInquiryId(Long id, Long inquiryId);
}
