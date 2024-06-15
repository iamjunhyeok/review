package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.ApplicationCancelRequest;
import com.iamjunhyeok.review.dto.CampaignApplyRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.ApplicationRepository;
import com.iamjunhyeok.review.repository.CampaignRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    private final UserRepository userRepository;

    private final CampaignRepository campaignRepository;

    @Transactional
    public Application apply(Long id, CampaignApplyRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());

        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());

        if (applicationRepository.existsByUserIdAndCampaignId(user.getId(), campaign.getId())) {
            throw ErrorCode.DUPLICATE_APPLICATION.build();
        }
        return applicationRepository.save(Application.create(user, campaign, request));
    }

    public Application findByIdAndCampaignId(Long campaignId, Long id) {
        return applicationRepository.findByIdAndCampaignId(id, campaignId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());
    }

    @Transactional
    public void cancel(Long campaignId, Long id, ApplicationCancelRequest request) {
        applicationRepository.findByIdAndCampaignId(id, campaignId)
                .ifPresentOrElse(application -> application.cancel(), () -> { throw ErrorCode.APPLICATION_NOT_FOUND.build(); });
    }

    @Transactional
    public void approve(Long campaignId, Long id) {
        applicationRepository.findByIdAndCampaignId(id, campaignId)
                .ifPresentOrElse(application -> application.approve(), () -> { throw ErrorCode.APPLICATION_NOT_FOUND.build(); });
    }

    @Transactional
    public void reject(Long campaignId, Long id) {
        applicationRepository.findByIdAndCampaignId(id, campaignId)
                .ifPresentOrElse(application -> application.reject(), () -> { throw ErrorCode.APPLICATION_NOT_FOUND.build(); });
    }
}
