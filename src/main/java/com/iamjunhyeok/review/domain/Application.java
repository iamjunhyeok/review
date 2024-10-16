package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.ApplicationReason;
import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.constant.ReviewStatus;
import com.iamjunhyeok.review.dto.request.CampaignApplyRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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

    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @Builder.Default
    @OneToMany(mappedBy = "application", cascade = CascadeType.PERSIST)
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "application", cascade = CascadeType.PERSIST)
    private List<ApplicationImage> images = new ArrayList<>();

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

    public void registerReview(Review review) {
        this.reviews.add(review);
        review.setStatus(ReviewStatus.CONFIRM_REQUEST);
        review.setApplication(this);
    }

    public void registerReviews(List<Review> reviews) {
        this.reviews.addAll(reviews);
        for (Review review : reviews) {
            review.setStatus(ReviewStatus.CONFIRM_REQUEST);
            review.setApplication(this);
        }
    }

    public void delete() {
        if (this.status == ApplicationStatus.APPLIED) {
            throw ErrorCode.APPLICATION_CANNOT_BE_DELETED.build();
        }
        this.deleted = true;
    }

    public void addImage(List<ApplicationImage> images) {
        if (CollectionUtils.isEmpty(images)) return;
        this.images.addAll(images);
        for (ApplicationImage image : images) {
            image.setApplication(this);
        }
    }

    public void reject() {
        if (this.status == ApplicationStatus.APPLIED) {
            this.setStatus(ApplicationStatus.REJECTED);
        } else {
            throw ErrorCode.CAMPAIGN_CANNOT_BE_REJECTED.build();
        }
    }
}
