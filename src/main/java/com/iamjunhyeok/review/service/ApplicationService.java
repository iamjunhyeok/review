package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.ApplicationReason;
import com.iamjunhyeok.review.constant.PenaltyReason;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.ApplicationCancelRequest;
import com.iamjunhyeok.review.dto.ApplicationViewProjection;
import com.iamjunhyeok.review.dto.CampaignApplyRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.ApplicationRepository;
import com.iamjunhyeok.review.repository.CampaignRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    private final UserRepository userRepository;

    private final CampaignRepository campaignRepository;

    private final PenaltyService penaltyService;

    @Transactional
    public Application apply(Long campaignId, CampaignApplyRequest request) {
        // 로그인 개발되면 수정할 것!!
        User user = userRepository.getReferenceById(1L);

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());

        if (applicationRepository.existsByUserIdAndCampaignId(user.getId(), campaign.getId())) {
            throw ErrorCode.DUPLICATE_APPLICATION.build();
        }

        Application application = Application.create(user, request);
        applicationRepository.save(application);

        campaign.apply(application);

        return application;
    }

    public Application findByIdAndCampaignId(Long campaignId, Long id) {
        return applicationRepository.findByIdAndCampaignId(id, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());
    }

    public ApplicationViewProjection findByIdAndCampaignIdWithCampaign(Long campaignId, Long id) {
        return applicationRepository.findApplicationByIdAndCampaignId(id, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());
    }

    @Transactional
    public void cancel(Long campaignId, Long id, ApplicationCancelRequest request) {
        applicationRepository.findByIdAndCampaignId(id, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build())
                .cancel();

        // 개인 사유일 경우 패널티 부여하기!!
        if (request.getReason() == ApplicationReason.USER_CANCEL) {
            penaltyService.create(1L, id, PenaltyReason.USER_CANCELLED);
        }
    }

    @Transactional
    public void approve(Long campaignId, Long id) {
        applicationRepository.findByIdAndCampaignId(id, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build())
                .approve();

        // 모집 인원만큼 모두 선발되면 나머지 인원은 미선정으로 업데이트해주기!!
    }

    public List<User> searchApplicants(Long campaignId) {
        return applicationRepository.findByCampaignIdWithUsers(campaignId)
                .stream()
                .map(Application::getUser)
                .toList();
    }
}
