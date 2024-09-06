package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.RegionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionMappingRepository extends JpaRepository<RegionMapping, Long> {

    @Query("select rm.code.id from RegionMapping rm where rm.regionGroup.id = :id")
    List<Long> findAllCodeIdByRegionGroupId(Long id);

    @Modifying
    @Query("delete from RegionMapping rm where rm.regionGroup.id = :id")
    void deleteByRegionGroupId(Long id);

    @Query("SELECT TRIM(TRAILING '0' FROM c.code) FROM Code c WHERE c.id IN (SELECT rm.code.id FROM RegionMapping rm WHERE rm.regionGroup.id = :regionGroupId)")
    List<Long> findTrimmedCodesByRegionGroupId(Long regionGroupId);
}
