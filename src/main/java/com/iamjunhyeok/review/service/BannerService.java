package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.projection.BannerSimpleProjection;
import com.iamjunhyeok.review.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public List<BannerSimpleProjection> fetchAll() {
        return bannerRepository.findAllByToday(LocalDate.now());
    }
}
