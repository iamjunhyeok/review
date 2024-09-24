package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, CustomNoticeRepository {

}
