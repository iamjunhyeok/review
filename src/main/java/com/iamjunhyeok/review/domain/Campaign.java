package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Campaign extends CampaignBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean deleted = false;

    public Campaign update(CampaignUpdateRequest request) {
        title = request.getTitle();
        capacity = request.getCapacity();
        applicationStartDate = request.getApplicationStartDate();
        applicationEndDate = request.getApplicationEndDate();
        announcementDate = request.getAnnouncementDate();
        useStartDate = request.getUseStartDate();
        useEndDate = request.getUseEndDate();
        reviewStartDate = request.getReviewStartDate();
        reviewEndDate = request.getReviewEndDate();
        offering = request.getOffering();
        keyword = request.getKeyword();
        hashtag = request.getHashtag();
        mission = request.getMission();
        guide = request.getGuide();
        information = request.getInformation();
        return this;
    }

    public void delete() {
        deleted = true;
    }
}


