package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Notice;
import com.iamjunhyeok.review.projection.NoticeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, CustomNoticeRepository {

    @Query("select n from Notice n where n.id = :id")
    Optional<NoticeProjection> fetchById(Long id);
}
