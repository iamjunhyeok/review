package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.dto.CampaignCreateRequest;
import com.iamjunhyeok.review.repository.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

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
                .build();

        Mockito.when(campaignRepository.save(any())).thenReturn(campaign);

        Campaign saved = campaignService.create(request);

        assertEquals(request.getTitle(), saved.getTitle());
        assertEquals(request.getCapacity(), saved.getCapacity());
        assertEquals(request.getApplicationStartDate(), saved.getApplicationStartDate());
        assertEquals(request.getApplicationEndDate(), saved.getApplicationEndDate());
        assertEquals(request.getAnnouncementDate(), saved.getAnnouncementDate());
        assertEquals(request.getUseStartDate(), saved.getUseStartDate());
        assertEquals(request.getUseEndDate(), saved.getUseEndDate());
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
                .build();

        Mockito.when(campaignRepository.save(any())).thenReturn(campaign);

        Campaign saved = campaignService.create(request);

        assertEquals(request.getTitle(), saved.getTitle());
        assertEquals(request.getCapacity(), saved.getCapacity());
        assertEquals(request.getApplicationStartDate(), saved.getApplicationStartDate());
        assertEquals(request.getApplicationEndDate(), saved.getApplicationEndDate());
        assertEquals(request.getAnnouncementDate(), saved.getAnnouncementDate());
        assertEquals(request.getUseStartDate(), saved.getUseStartDate());
        assertEquals(request.getUseEndDate(), saved.getUseEndDate());
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
        request.setUseStartDate(LocalDate.MAX);
        request.setUseEndDate(LocalDate.MAX);
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
}