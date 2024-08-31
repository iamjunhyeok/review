package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertiserRepository extends JpaRepository<Advertiser, Long>, CustomAdvertiserRepository {
}
