package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Sns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SnsRepository extends JpaRepository<Sns, String> {

    Optional<Sns> findBySnsId(String snsId);
}
