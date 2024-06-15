package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Voca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocaRepository extends JpaRepository<Voca, Long> {
}
