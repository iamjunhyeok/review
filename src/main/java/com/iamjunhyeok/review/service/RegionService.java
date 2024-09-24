package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Code;
import com.iamjunhyeok.review.domain.RegionGroup;
import com.iamjunhyeok.review.dto.request.RegionGroupRegisterRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.RegionGroupProjection;
import com.iamjunhyeok.review.repository.CodeRepository;
import com.iamjunhyeok.review.repository.RegionGroupRepository;
import com.iamjunhyeok.review.repository.RegionMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService {

    private final RegionGroupRepository regionGroupRepository;
    private final RegionMappingRepository regionMappingRepository;
    private final CodeRepository codeRepository;

    public List<RegionGroupProjection> getAllRegionGroups() {
        return regionGroupRepository.fetchAll();
    }

    public List<Long> getAll(Long id) {
        return regionMappingRepository.findAllCodeIdByRegionGroupId(id);
    }

    @Transactional
    public void registerRegionGroup(RegionGroupRegisterRequest request) {
        regionGroupRepository.save(RegionGroup.of(request.getName(), request.getDescription(), request.getOrder()));
    }

    @Transactional
    public void modify(Long id, List<Long> codeIds) {
        regionMappingRepository.deleteByRegionGroupId(id);

        RegionGroup regionGroup = regionGroupRepository.findById(id)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());
        codeIds.forEach(codeId -> {
            Code code = codeRepository.getReferenceById(codeId);
            regionGroup.addRegion(code);
        });
    }
}
