package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Campaign extends CampaignBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean deleted = false;

    @OneToMany(mappedBy = "campaign")
    private Set<Application> applications = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.PERSIST)
    private List<CampaignLink> links = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.PERSIST)
    private List<CampaignImage> images = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.PERSIST)
    private List<CampaignMission> missions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.PERSIST)
    private List<CampaignOption> options = new ArrayList<>();

    public Campaign apply(Application application) {
        this.applications.add(application);
        application.setCampaign(this);
        return this;
    }

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
        this.offeringSummary = request.getOfferingSummary();
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
        this.storeInformation = request.getStoreInformation();
        return this;
    }

    public void delete() {
        this.deleted = true;
    }

    public void addLink(List<CampaignLink> links) {
        this.links.addAll(links);
        for (CampaignLink link : links) {
            link.setCampaign(this);
        }
    }

    public void addImage(List<CampaignImage> images) {
        this.images.addAll(images);
        for (CampaignImage image : images) {
            image.setCampaign(this);
        }
    }

    public void addMission(List<Code> missions, List<String> arguments) {
        for (int i = 0; i < missions.size(); i++) {
            this.missions.add(CampaignMission.of(arguments.get(i),this, missions.get(i)));
        }
    }

    public void addOption(List<Code> options) {
        for (Code option : options) {
            this.options.add(CampaignOption.of(this, option));
        }
    }
}


