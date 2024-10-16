package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Banner;
import com.iamjunhyeok.review.domain.Code;
import com.iamjunhyeok.review.domain.Plan;
import com.iamjunhyeok.review.dto.request.BannerRegisterRequest;
import com.iamjunhyeok.review.dto.request.PlanRegisterRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.AdvertiserProjection;
import com.iamjunhyeok.review.projection.BannerProjection;
import com.iamjunhyeok.review.projection.PlanProjection;
import com.iamjunhyeok.review.repository.AdvertiserRepository;
import com.iamjunhyeok.review.repository.BannerRepository;
import com.iamjunhyeok.review.repository.CampaignRepository;
import com.iamjunhyeok.review.repository.CodeRepository;
import com.iamjunhyeok.review.repository.PlanRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import com.iamjunhyeok.review.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final CodeRepository codeRepository;
    private final BannerRepository bannerRepository;
    private final PlanRepository planRepository;
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;
    private final AdvertiserRepository advertiserRepository;

    private final S3Util s3Util;

    @Transactional
    public void registerBanner(BannerRegisterRequest request, MultipartFile file) throws IOException {
        Code screen = codeRepository.getReferenceById(request.getScreenCodeId());
        Banner banner = Banner.builder()
                .imageName(file.getOriginalFilename())
                .linkUrl(request.getLinkUrl())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .order(request.getOrder())
                .screen(screen)
                .build();
        bannerRepository.save(banner);

        s3Util.putObject("olim-banner", file);
    }

    public List<BannerProjection> fetchAll(Pageable pageable) {
        return bannerRepository.fetchAll(pageable);
    }

    @Transactional
    public void registerPlan(PlanRegisterRequest request) {
        Plan plan = Plan.builder()
                .name(request.getName())
                .campaignCount(request.getCampaignCount())
                .peopleCount(request.getPeopleCount())
                .price(request.getPrice())
                .discountPrice(request.getDiscountPrice())
                .reportDownloadDays(request.getReportDownloadDays())
                .campaignRegistrationType(request.getCampaignRegistrationType())
                .reviewerSelectionType(request.getReviewerSelectionType())
                .pointPaymentType(request.getPointPaymentType())
                .build();
        planRepository.save(plan);
    }

    public List<PlanProjection> fetchAllPlans() {
        return planRepository.fetchAll();
    }

    @Transactional
    public void subscribe(Long userId, Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> ErrorCode.PLAN_NOT_FOUND.build());

        userRepository.findById(userId)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build())
                .subscribe(plan);
    }

    public List<AdvertiserProjection> fetchAllAdvertisers(String status) {
        return advertiserRepository.fetchAll(status);
    }
}
