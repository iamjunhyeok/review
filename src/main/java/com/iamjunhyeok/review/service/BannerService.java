package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.projection.BannerProjection;
import com.iamjunhyeok.review.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public List<BannerProjection> fetchAll() {
        return bannerRepository.fetchAllValidBanners();
    }
}
