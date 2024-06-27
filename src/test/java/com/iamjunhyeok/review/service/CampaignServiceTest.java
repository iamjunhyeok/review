package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.dto.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import com.iamjunhyeok.review.exception.ApplicationException;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CampaignServiceTest {

    @Autowired
    private CampaignService campaignService;

    @MockBean
    private CampaignRepository campaignRepository;

    @Test
    void 캠페인등록_오늘부터신청시작되는캠페인등록() {
        CampaignCreateRequest request = getCampaignCreateRequest();
        request.setApplicationStartDate(LocalDate.now());

        Campaign campaign = Campaign.builder()
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
                .status(request.getApplicationStartDate().isAfter(LocalDate.now()) ? CampaignStatus.PLANNED : CampaignStatus.ONGOING)
                .build();

        when(campaignRepository.save(any())).thenReturn(campaign);

        Campaign saved = campaignService.create(request);

        assertEquals(request.getTitle(), saved.getTitle());
        assertEquals(request.getCapacity(), saved.getCapacity());
        assertEquals(request.getApplicationStartDate(), saved.getApplicationStartDate());
        assertEquals(request.getApplicationEndDate(), saved.getApplicationEndDate());
        assertEquals(request.getAnnouncementDate(), saved.getAnnouncementDate());
        assertEquals(request.getReviewStartDate(), saved.getReviewStartDate());
        assertEquals(request.getReviewEndDate(), saved.getReviewEndDate());
        assertEquals(request.getOffering(), saved.getOffering());
        assertEquals(request.getKeyword(), saved.getKeyword());
        assertEquals(request.getHashtag(), saved.getHashtag());
        assertEquals(request.getMission(), saved.getMission());
        assertEquals(request.getGuide(), saved.getGuide());
        assertEquals(request.getInformation(), saved.getInformation());
        assertEquals(CampaignStatus.ONGOING, saved.getStatus());
    }

    @Test
    void 캠페인등록_오늘이후에신청시작되는캠페인등록() {
        CampaignCreateRequest request = getCampaignCreateRequest();

        Campaign campaign = Campaign.builder()
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
                .status(request.getApplicationStartDate().isAfter(LocalDate.now()) ? CampaignStatus.PLANNED : CampaignStatus.ONGOING)
                .build();

        when(campaignRepository.save(any())).thenReturn(campaign);

        Campaign saved = campaignService.create(request);

        assertEquals(request.getTitle(), saved.getTitle());
        assertEquals(request.getCapacity(), saved.getCapacity());
        assertEquals(request.getApplicationStartDate(), saved.getApplicationStartDate());
        assertEquals(request.getApplicationEndDate(), saved.getApplicationEndDate());
        assertEquals(request.getAnnouncementDate(), saved.getAnnouncementDate());
        assertEquals(request.getReviewStartDate(), saved.getReviewStartDate());
        assertEquals(request.getReviewEndDate(), saved.getReviewEndDate());
        assertEquals(request.getOffering(), saved.getOffering());
        assertEquals(request.getKeyword(), saved.getKeyword());
        assertEquals(request.getHashtag(), saved.getHashtag());
        assertEquals(request.getMission(), saved.getMission());
        assertEquals(request.getGuide(), saved.getGuide());
        assertEquals(request.getInformation(), saved.getInformation());
        assertEquals(CampaignStatus.PLANNED, saved.getStatus());
    }

    private static CampaignCreateRequest getCampaignCreateRequest() {
        CampaignCreateRequest request = new CampaignCreateRequest();
        request.setTitle("타이틀");
        request.setCapacity(3);
        request.setApplicationStartDate(LocalDate.MAX);
        request.setApplicationEndDate(LocalDate.MAX);
        request.setAnnouncementDate(LocalDate.MAX);
        request.setReviewStartDate(LocalDate.MAX);
        request.setReviewEndDate(LocalDate.MAX);
        request.setOffering("제공내용");
        request.setKeyword("필수키워드");
        request.setHashtag("해시태그");
        request.setMission("미션");
        request.setGuide("작성가이드");
        request.setInformation("안내사항");
        return request;
    }

    @Test
    void 캠페인수정_존재하지않는캠페인() {
        CampaignUpdateRequest request = getCampaignUpdateRequest();

        Long id = 1L;
        when(campaignRepository.findById(id)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> campaignService.update(id, request));

        assertEquals(ErrorCode.CAMPAIGN_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
        assertEquals(ErrorCode.CAMPAIGN_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void 캠페인수정_존재하는캠페인() {
        CampaignUpdateRequest request = getCampaignUpdateRequest();

        Campaign campaign = new Campaign();

        Long id = 1L;
        when(campaignRepository.findById(id)).thenReturn(Optional.of(campaign));

        Campaign updated = campaignService.update(id, request);

        assertEquals(request.getTitle(), updated.getTitle());
        assertEquals(request.getCapacity(), updated.getCapacity());
        assertEquals(request.getApplicationStartDate(), updated.getApplicationStartDate());
        assertEquals(request.getApplicationEndDate(), updated.getApplicationEndDate());
        assertEquals(request.getAnnouncementDate(), updated.getAnnouncementDate());
        assertEquals(request.getReviewStartDate(), updated.getReviewStartDate());
        assertEquals(request.getReviewEndDate(), updated.getReviewEndDate());
        assertEquals(request.getOffering(), updated.getOffering());
        assertEquals(request.getKeyword(), updated.getKeyword());
        assertEquals(request.getHashtag(), updated.getHashtag());
        assertEquals(request.getMission(), updated.getMission());
        assertEquals(request.getGuide(), updated.getGuide());
        assertEquals(request.getInformation(), updated.getInformation());
    }

    private static CampaignUpdateRequest getCampaignUpdateRequest() {
        CampaignUpdateRequest request = new CampaignUpdateRequest();
        request.setTitle("타이틀");
        request.setCapacity(3);
        request.setApplicationStartDate(LocalDate.MAX);
        request.setApplicationEndDate(LocalDate.MAX);
        request.setAnnouncementDate(LocalDate.MAX);
        request.setReviewStartDate(LocalDate.MAX);
        request.setReviewEndDate(LocalDate.MAX);
        request.setOffering("제공내용");
        request.setKeyword("필수키워드");
        request.setHashtag("해시태그");
        request.setMission("미션");
        request.setGuide("작성가이드");
        request.setInformation("안내사항");
        return request;
    }

    @Test
    void 캠페인삭제_존재하지않는캠페인() {
        Long id = 1L;
        when(campaignRepository.findById(id)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> campaignService.delete(id));

        assertEquals(ErrorCode.CAMPAIGN_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
        assertEquals(ErrorCode.CAMPAIGN_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void 캠페인삭제_존재하는캠페인() {
        Long id = 1L;
        Campaign campaign = new Campaign();
        when(campaignRepository.findById(id)).thenReturn(Optional.of(campaign));

        campaignService.delete(id);

        assertEquals(true, campaign.isDeleted());
    }
}