package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.User;
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

        return applicationRepository.save(Application.create(user, campaign, request));
    }

    public Application findById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());
    }

    @Transactional
    public Application cancel(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());
        return application.cancel();
    }
}
