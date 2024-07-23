package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Faq;
import com.iamjunhyeok.review.projection.FaqProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long>, CustomFaqRepository {

    @Query("select f from Faq f where f.id = :id")
    Optional<FaqProjection> fetchOne(Long id);
}
