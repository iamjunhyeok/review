package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.InquiryAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswer, Long> {
    Optional<InquiryAnswer> findByIdAndInquiryId(Long id, Long inquiryId);
}
