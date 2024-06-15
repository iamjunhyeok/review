package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.CampaignApplyRequest;
import com.iamjunhyeok.review.exception.ApplicationException;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.ApplicationRepository;
import com.iamjunhyeok.review.repository.CampaignRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @InjectMocks
    private ApplicationService applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CampaignRepository campaignRepository;


    @BeforeEach
    void setUp() {

    }

    @Test
    void 캠페인신청_유효함_신청성공() {
        Long id = 1L;
        CampaignApplyRequest request = CampaignApplyRequest.of("전준혁", "01076782457");

        User user = User.createUser("jeonjhyeok@gmail.com", "jeonjhyeok", "1234", "1234");

        Campaign campaign = Campaign.builder()
                .title("타이틀")
                .capacity(10)
                .applicationStartDate(LocalDate.MAX)
                .applicationEndDate(LocalDate.MAX)
                .announcementDate(LocalDate.MAX)
                .useStartDate(LocalDate.MAX)
                .useEndDate(LocalDate.MAX)
                .reviewStartDate(LocalDate.MAX)
                .reviewEndDate(LocalDate.MAX)
                .offering("제공내용")
                .keyword("키워드")
                .hashtag("해시태그")
                .mission("미션")
                .guide("가이드")
                .information("안내")
                .status(CampaignStatus.ONGOING)
                .build();

        Application application = Application.create(user, campaign, request);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(campaignRepository.findById(id)).thenReturn(Optional.of(campaign));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        Application saved = applicationService.apply(id, request);

        assertEquals(request.getName(), saved.getName());
        assertEquals(request.getPhoneNumber(), saved.getPhoneNumber());
        assertEquals(ApplicationStatus.APPLIED, saved.getStatus());
    }

    @Test
    void 캠페인신청_사용자없음_예외발생() {
        Long id = 1L;
        CampaignApplyRequest request = new CampaignApplyRequest();
        request.setName("전준혁");
        request.setPhoneNumber("01076782457");

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> applicationService.apply(id, request));

        assertEquals(ErrorCode.USER_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
        assertEquals(ErrorCode.USER_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void 캠페인신청_캠페인없음_예외발생() {
        Long id = 1L;
        CampaignApplyRequest request = new CampaignApplyRequest();
        request.setName("전준혁");
        request.setPhoneNumber("01076782457");

        when(userRepository.findById(id)).thenReturn(Optional.of(User.createUser("jeonjhyeok@gmail.com", "jeonjhyeok", "1234", "1234")));
        when(campaignRepository.findById(id)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> applicationService.apply(id, request));

        assertEquals(ErrorCode.CAMPAIGN_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
        assertEquals(ErrorCode.CAMPAIGN_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void 캠페인신청_중복신청_예외발생() {
        Long userId = 1L;
        User user = User.createUser("jeonjhyeok@gmail.com", "jeonjhyeok", "1234", "1234");

        Long campaignId = 1L;
        Campaign campaign = Campaign.builder()
                .id(campaignId)
                .status(CampaignStatus.PLANNED)
                .build();

        CampaignApplyRequest request = CampaignApplyRequest.of("전준혁", "01076782457");

        Application application = Application.create(user, campaign, request);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));
        when(applicationRepository.findByUserIdAndCampaignId(user.getId(), campaign.getId())).thenReturn(Optional.of(application));

        ApplicationException exception = assertThrows(ApplicationException.class, () -> applicationService.apply(campaignId, request));

        assertEquals(ErrorCode.DUPLICATE_APPLICATION.getHttpStatus(), exception.getHttpStatus());
        assertEquals(ErrorCode.DUPLICATE_APPLICATION.getMessage(), exception.getMessage());
    }

    @Test
    void 캠페인신청_진행상태아님_예외발생() {
        Long campaignId = 1L;
        Campaign campaign = Campaign.builder()
                .id(campaignId)
                .status(CampaignStatus.PLANNED)
                .build();

        CampaignApplyRequest request = CampaignApplyRequest.of("전준혁", "01076782457");

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));

        ApplicationException exception = assertThrows(ApplicationException.class, () -> applicationService.apply(campaignId, request));

        assertEquals(ErrorCode.NOT_ONGOING_CAMPAIGN.getHttpStatus(), exception.getHttpStatus());
        assertEquals(ErrorCode.NOT_ONGOING_CAMPAIGN.getMessage(), exception.getMessage());
    }

    @Test
    void 캠페인조회_유효함_조회성공() {
        Long userId = 1L;
        User user = User.createUser("jeonjhyeok@gmail.com", "jeonjhyeok", "1234", "1234");

        Long campaignId = 1L;
        Campaign campaign = Campaign.builder()
                .id(campaignId)
                .status(CampaignStatus.PLANNED)
                .build();

        CampaignApplyRequest request = CampaignApplyRequest.of("전준혁", "01076782457");

        Application application = Application.create(user, campaign, request);

        Long id = 1L;
        when(applicationRepository.findById(id)).thenReturn(Optional.of(application));

        Application find = applicationService.findByIdAndCampaignId(id, id);

        assertEquals(request.getName(), find.getName());
        assertEquals(request.getPhoneNumber(), find.getPhoneNumber());
        assertEquals(user.getId(), find.getUser().getId());
        assertEquals(campaign.getId(), find.getCampaign().getId());
    }

    @Test
    void 캠페인조회_캠페인없음_예외발생() {
        Long userId = 1L;
        User user = User.createUser("jeonjhyeok@gmail.com", "jeonjhyeok", "1234", "1234");

        Long campaignId = 1L;
        Campaign campaign = Campaign.builder()
                .id(campaignId)
                .status(CampaignStatus.PLANNED)
                .build();

        CampaignApplyRequest request = CampaignApplyRequest.of("전준혁", "01076782457");

        Application application = Application.create(user, campaign, request);


        Long id = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(campaignRepository.findById(userId)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> applicationService.apply(campaignId, request));

        assertEquals(ErrorCode.CAMPAIGN_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
        assertEquals(ErrorCode.CAMPAIGN_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void 캠페인취소_유효함_취소성공() {
        fail();
    }

    @Test
    void 캠페인취소_캠페인없음_예외발생() {
        fail();
    }

    @Test
    void 캠페인취소_이미취소_예외발생() {
        fail();
    }
}