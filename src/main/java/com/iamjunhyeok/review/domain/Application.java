package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.ApplicationReason;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.dto.CampaignApplyRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Application extends Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Enumerated(EnumType.STRING)
    private ApplicationReason reason;

    private String details;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    public static Application create(User user, CampaignApplyRequest request) {
        Application application = new Application();
        application.setUser(user);
        application.setName(request.getName());
        application.setPhoneNumber(request.getPhoneNumber());
        application.setStatus(ApplicationStatus.APPLIED);
        application.setAddress(request.getAddress());
        application.setRest(request.getRest());
        application.setPostalCode(request.getPostalCode());
        return application;
    }

    public void cancel() {
        if (this.status == ApplicationStatus.APPLIED || this.status == ApplicationStatus.APPROVED) {
            this.setStatus(ApplicationStatus.CANCELLED);
        } else {
            throw ErrorCode.CAMPAIGN_CANNOT_BE_CANCELED.build();
        }
    }

    public void approve() {
        if (this.status == ApplicationStatus.APPLIED) {
            this.setStatus(ApplicationStatus.APPROVED);
        } else {
            throw ErrorCode.CAMPAIGN_CANNOT_BE_APPROVED.build();
        }
    }
}
