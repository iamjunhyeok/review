package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long>, CustomInquiryRepository {

    @Query("select i.user.id from Inquiry i where i.id = :id")
    Long findUserIdByInquiryId(Long id);
}
