package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.CampaignCategory;
import com.iamjunhyeok.review.constant.CampaignSocial;
import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.constant.CampaignType;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.CampaignLink;
import com.iamjunhyeok.review.dto.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.CampaignSearchProjection;
import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import com.iamjunhyeok.review.dto.CampaignViewResponse;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.CampaignLinkRepository;
import com.iamjunhyeok.review.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    public List<CampaignSearchProjection> search(String type, String category, String filter, Pageable pageable) {
        return campaignRepository.search(type, category, filter, pageable);
    }

    public CampaignViewResponse view(Long id) {
        List<Object[]> findBy = campaignRepository.findBy(id)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());
        return transform(findBy);
    }

    private CampaignViewResponse transform(List<Object[]> rs) {
        CampaignViewResponse response = new CampaignViewResponse();
        for (Object[] row : rs) {
            response.setId((Long) row[0]);
            response.setType((CampaignType) row[1]);
            response.setCategory((CampaignCategory) row[2]);
            response.setSocial((CampaignSocial) row[3]);
            response.setTitle((String) row[4]);
            response.setCapacity((Integer) row[5]);
            response.setApplicationStartDate((LocalDate) row[6]);
            response.setApplicationEndDate((LocalDate) row[7]);
            response.setAnnouncementDate((LocalDate) row[8]);
            response.setReviewStartDate((LocalDate) row[9]);
            response.setReviewEndDate((LocalDate) row[10]);
            response.setOffering((String) row[11]);
            response.setKeyword((String) row[12]);
            response.setHashtag((String) row[13]);
            response.setMission((String) row[14]);
            response.setGuide((String) row[15]);
            response.setInformation((String) row[16]);
            response.setStatus((CampaignStatus) row[17]);
            response.setAddress((String) row[18]);
            response.setRest((String) row[19]);
            response.setPostalCode((String) row[20]);
            response.setLongitude((String) row[21]);
            response.setLatitude((String) row[22]);
            response.getLinks().add((String) row[23]);
        }
        return response;
    }
}
