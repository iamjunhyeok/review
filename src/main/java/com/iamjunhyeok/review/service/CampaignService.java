package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.dto.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public Campaign create(CampaignCreateRequest request) {
        return campaignRepository.save(
                Campaign.builder()
                        .title(request.getTitle())
                        .capacity(request.getCapacity())
                        .applicationStartDate(request.getApplicationStartDate())
                        .applicationEndDate(request.getApplicationEndDate())
                        .announcementDate(request.getAnnouncementDate())
                        .useStartDate(request.getUseStartDate())
                        .useEndDate(request.getUseEndDate())
                        .reviewStartDate(request.getReviewStartDate())
                        .reviewEndDate(request.getReviewEndDate())
                        .offering(request.getOffering())
                        .keyword(request.getKeyword())
                        .hashtag(request.getHashtag())
                        .mission(request.getMission())
                        .guide(request.getGuide())
                        .information(request.getInformation())
                        .status(request.getApplicationStartDate().isAfter(LocalDate.now()) ? CampaignStatus.PLANNED : CampaignStatus.ONGOING)
                        .build()
        );
    }

    @Transactional
    public Campaign update(Long id, CampaignUpdateRequest request) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());
        return campaign.update(request);
    }

    @Transactional
    public void delete(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());
        campaign.delete();
    }
}
