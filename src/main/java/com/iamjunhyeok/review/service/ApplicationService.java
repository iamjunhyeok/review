package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.ApplicationReason;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.constant.PenaltyReason;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.ApplicationImage;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.request.ApplicationCancelRequest;
import com.iamjunhyeok.review.dto.request.CampaignApplyRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.ApplicantProjection;
import com.iamjunhyeok.review.projection.ApplicationView;
import com.iamjunhyeok.review.projection.ApplicationSearchProjection;
import com.iamjunhyeok.review.projection.CampaignViewProjection;
import com.iamjunhyeok.review.projection.UserCampaignApplicationProjection;
import com.iamjunhyeok.review.repository.ApplicationRepository;
import com.iamjunhyeok.review.repository.CampaignRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import com.iamjunhyeok.review.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    private final UserRepository userRepository;

    private final CampaignRepository campaignRepository;

    private final PenaltyService penaltyService;

    private final S3Util s3Util;

    private final CampaignService campaignService;

    @Transactional
    public Application apply(Long campaignId, CampaignApplyRequest request, Long userId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());

        if (applicationRepository.existsByCampaignIdAndUserIdAndStatus(campaignId, userId, ApplicationStatus.APPLIED)) {
            throw ErrorCode.DUPLICATE_APPLICATION.build();
        }

        User user = userRepository.getReferenceById(userId);
        Application application = Application.create(user, request);
        applicationRepository.save(application);

        campaign.apply(application);
        return application;
    }

    public Application findByIdAndCampaignId(Long campaignId, Long id) {
        return applicationRepository.findByIdAndCampaignId(id, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());
    }

    public ApplicationView fetchOne(Long campaignId, Long applicationId) {
        return applicationRepository.fetchOne(campaignId, applicationId);
    }

    @Transactional
    public void cancel(Long campaignId, Long applicationId, ApplicationCancelRequest request, List<MultipartFile> files, Long userId) throws IOException {
        Application application = applicationRepository.findByIdAndCampaignId(applicationId, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());
        application.cancel();

        if (request.getReason() == ApplicationReason.USER_CANCEL) {
            User user = userRepository.getReferenceById(userId);
            penaltyService.givePenaltyPoints(user, application, PenaltyReason.USER_CANCELLED);
        }

        List<ApplicationImage> images = Optional.ofNullable(files)
                .orElse(Collections.emptyList())
                .stream()
                .map(MultipartFile::getOriginalFilename)
                .map(ApplicationImage::of)
                .toList();
        application.addImage(images);

        putObjectAllFiles(files);
    }

    private void putObjectAllFiles(List<MultipartFile> files) throws IOException {
        if (CollectionUtils.isEmpty(files)) return;
        for (MultipartFile file : files) {
            s3Util.putObject(file);
        }
    }

    @Transactional
    public void approve(Long campaignId, Long applicationId) {
        applicationRepository.findByIdAndCampaignId(applicationId, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build())
                .approve();

        // 모집 인원만큼 모두 선발되면 나머지 인원은 미선정으로 업데이트해주기!!
        // 캠페인 모집인원 수 조회
        // redis 적용하기
        CampaignViewProjection campaignViewProjection = campaignService.fetchById(campaignId);
        if (Long.valueOf(campaignViewProjection.getCapacity()) == campaignViewProjection.getApplicantsCount()) {
            // 신청된 상태의 application 조회
            applicationRepository.findAllByCampaignIdAndStatus(campaignId, ApplicationStatus.APPLIED)
                    .forEach(Application::reject);
        }
    }

    public List<ApplicantProjection> fetchAllApplicants(Long campaignId) {
        return applicationRepository.fetchAllApplicants(campaignId);
    }

    public List<UserCampaignApplicationProjection> fetchAuthenticatedUserCampaignApplication(Long campaignId, Long applicationId) {
        return applicationRepository.fetchAuthenticatedUserCampaignApplication(campaignId, applicationId);
    }

    @Transactional
    public void delete(Long campaignId, Long applicationId) {
        applicationRepository.findByIdAndCampaignId(applicationId, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build())
                .delete();
    }

    public boolean checkApplied(Long campaignId, CustomOAuth2User principal) {
        if (principal == null) return false;
        return applicationRepository.existsByCampaignIdAndUserIdAndStatus(campaignId, principal.getUserId(), ApplicationStatus.APPLIED);
    }

    public List<ApplicationSearchProjection> fetchAllApplications(String status, Long userId) {
        return applicationRepository.fetchAllApplications(status, userId);
    }
}
