package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    @Query("select u from users u left join fetch u.penalties where u.id = :id")
    Optional<User> findByIdWithPenalties(Long id);
}
