package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.CampaignLink;
import com.iamjunhyeok.review.dto.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.CampaignSearchProjection;
import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.CampaignLinkRepository;
import com.iamjunhyeok.review.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CampaignService {
    private final CampaignRepository campaignRepository;
    private final CampaignLinkRepository campaignLinkRepository;

    @Transactional
    public Campaign create(CampaignCreateRequest request) {
        Campaign campaign = campaignRepository.save(
                Campaign.builder()
                        .type(request.getType())
                        .category(request.getCategory())
                        .social(request.getSocial())
                        .title(request.getTitle())
                        .capacity(request.getCapacity())
                        .applicationStartDate(request.getApplicationStartDate())
                        .applicationEndDate(request.getApplicationEndDate())
                        .announcementDate(request.getAnnouncementDate())
                        .reviewStartDate(request.getReviewStartDate())
                        .reviewEndDate(request.getReviewEndDate())
                        .offering(request.getOffering())
                        .keyword(request.getKeyword())
                        .hashtag(request.getHashtag())
                        .mission(request.getMission())
                        .guide(request.getGuide())
                        .information(request.getInformation())
                        .address(request.getAddress())
                        .rest(request.getRest())
                        .postalCode(request.getPostalCode())
                        .longitude(request.getLongitude())
                        .latitude(request.getLatitude())
                        .status(request.getApplicationStartDate().isAfter(LocalDate.now()) ? CampaignStatus.PLANNED : CampaignStatus.ONGOING)
                        .build()
        );

        List<CampaignLink> links = request.getLinks().stream()
                .map(CampaignLink::of)
                .toList();
        campaign.addLink(links);

        return campaign;
    }

    @Transactional
    public Campaign update(Long id, CampaignUpdateRequest request) {
        Campaign campaign = campaignRepository.findByIdWithLink(id)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());
        campaign.update(request);

        List<CampaignLink> links = request.getLinks().stream()
                .map(CampaignLink::of)
                .toList();

        campaignLinkRepository.deleteByCampaignId(campaign.getId());
        campaign.getLinks().clear();

        campaign.addLink(links);

        return campaign;
    }

    @Transactional
    public void delete(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());
        campaign.delete();
    }

    public List<CampaignSearchProjection> search(String query) {
        return campaignRepository.search(query);
    }
}
