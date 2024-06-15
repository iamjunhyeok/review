package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.CampaignApplyRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Test
    void findByUserIdAndCampaignId_유효함_조회성공() {
        User user = User.createUser("jeonjhyeok@gmail.com", "jeonjhyeok", "1234", "1234");
        userRepository.save(user);

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
        campaignRepository.save(campaign);

        CampaignApplyRequest request = CampaignApplyRequest.of("전준혁", "01076782457");

        applicationRepository.save(Application.create(user, campaign, request));

        Optional<Application> application = applicationRepository.findByUserIdAndCampaignId(user.getId(), campaign.getId());
        assertTrue(application.isPresent());
        assertEquals(campaign.getId(), application.get().getCampaign().getId());
        assertEquals(user.getId(), application.get().getUser().getId());
    }

    @Test
    void findByUserIdAndCampaignId_데이터없음_조회실패() {
        Optional<Application> application = applicationRepository.findByUserIdAndCampaignId(1L, 1L);
        assertTrue(application.isEmpty());
    }
}