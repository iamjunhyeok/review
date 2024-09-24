package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.CampaignImage;
import com.iamjunhyeok.review.projection.CampaignImageNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignImageRepository extends JpaRepository<CampaignImage, Long> {

    List<CampaignImageNameProjection> findByIdIn(List<Long> ids);

    void deleteAllByNameIn(List<String> names);
}
