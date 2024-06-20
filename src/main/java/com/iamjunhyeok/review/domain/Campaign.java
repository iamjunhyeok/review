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
    @Column(nullable = false)
    private CampaignStatus status;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean deleted = false;

    public Campaign update(CampaignUpdateRequest request) {
        this.setType(request.getType());
        this.setSocial(request.getSocial());
        this.setTitle(request.getTitle());
        this.setCapacity(request.getCapacity());
        this.setApplicationStartDate(request.getApplicationStartDate());
        this.setApplicationEndDate(request.getApplicationEndDate());
        this.setAnnouncementDate(request.getAnnouncementDate());
        this.setUseStartDate(request.getUseStartDate());
        this.setUseEndDate(request.getUseEndDate());
        this.setReviewStartDate(request.getReviewStartDate());
        this.setReviewEndDate(request.getReviewEndDate());
        this.setOffering(request.getOffering());
        this.setKeyword(request.getKeyword());
        this.setHashtag(request.getHashtag());
        this.setMission(request.getMission());
        this.setGuide(request.getGuide());
        this.setInformation(request.getInformation());
        this.setAddress(request.getAddress());
        this.setRest(request.getRest());
        this.setPostalCode(request.getPostalCode());
        this.setLongitude(request.getLongitude());
        this.setLatitude(request.getLatitude());
        return this;
    }

    public void delete() {
        this.setDeleted(true);
    }
}


