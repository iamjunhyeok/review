package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Inquiry;
import com.iamjunhyeok.review.dto.InquiryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long>, CustomInquiryRepository {

    @Query("select i from Inquiry i where i.id = :id")
    Optional<InquiryProjection> fetchOne(Long id);
}
