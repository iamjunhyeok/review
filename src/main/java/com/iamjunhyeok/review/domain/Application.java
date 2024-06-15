package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.dto.CampaignApplyRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Application extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    public static Application create(User user, Campaign campaign, CampaignApplyRequest request) {
        Application application = new Application();
        application.setUser(user);
        application.setCampaign(campaign);
        application.setName(request.getName());
        application.setPhoneNumber(request.getPhoneNumber());
        application.setStatus(ApplicationStatus.APPLIED);
        return application;
    }

    public void cancel() {
        if (this.status == ApplicationStatus.APPLIED || this.status == ApplicationStatus.APPROVED) {
            this.setStatus(ApplicationStatus.CANCELLED);
        } else {
            throw ErrorCode.CAMPAIGN_CANNOT_BE_CANCELED.build();
        }
    }
}
