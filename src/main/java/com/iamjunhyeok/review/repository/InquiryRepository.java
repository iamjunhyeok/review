package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    Optional<Inquiry> findByIdAndAnswerId(Long id, Long answerId);
}
