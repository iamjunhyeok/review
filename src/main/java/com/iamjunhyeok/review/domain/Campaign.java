package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean deleted = false;

    @OneToMany(mappedBy = "campaign")
    private List<Application> applications = new ArrayList<>();

    public Campaign update(CampaignUpdateRequest request) {
        this.type = request.getType();
        this.category = request.getCategory();
        this.social = request.getSocial();
        this.title = request.getTitle();
        this.capacity = request.getCapacity();
        this.applicationStartDate = request.getApplicationStartDate();
        this.applicationEndDate = request.getApplicationEndDate();
        this.announcementDate = request.getAnnouncementDate();
        this.reviewStartDate = request.getReviewStartDate();
        this.reviewEndDate = request.getReviewEndDate();
        this.offering = request.getOffering();
        this.keyword = request.getKeyword();
        this.hashtag = request.getHashtag();
        this.mission = request.getMission();
        this.guide = request.getGuide();
        this.information = request.getInformation();
        this.address = request.getAddress();
        this.rest = request.getRest();
        this.postalCode = request.getPostalCode();
        this.longitude = request.getLongitude();
        this.latitude = request.getLatitude();
        return this;
    }

    public void delete() {
        this.deleted = true;
    }
}


