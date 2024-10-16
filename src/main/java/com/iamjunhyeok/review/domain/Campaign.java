package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.dto.request.CampaignUpdateRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_code_id")
    private Code typeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code_id")
    private Code categoryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "social_code_id")
    private Code socialCode;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean deleted;

    @Builder.Default
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "campaign")
    private List<Favourite> favourites = new ArrayList<>();

    public Campaign apply(Application application) {
        this.applications.add(application);
        application.setCampaign(this);
        return this;
    }

    public Campaign update(CampaignUpdateRequest request) {
//        this.type = request.getType();
//        this.category = request.getCategory();
//        this.social = request.getSocial();
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

    public void update(Code typeCode, Code categoryCode, Code socialCode, CampaignUpdateRequest request) {
        this.typeCode = typeCode;
        this.categoryCode = categoryCode;
        this.socialCode = socialCode;
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
        this.guide = request.getGuide();
        this.information = request.getInformation();
        this.address = request.getAddress();
        this.rest = request.getRest();
        this.postalCode = request.getPostalCode();
        this.longitude = request.getLongitude();
        this.latitude = request.getLatitude();
        this.storeInformation = request.getStoreInformation();
    }

    public void delete() {
        this.deleted = true;
    }

    public void addLink(List<CampaignLink> links) {
        if (CollectionUtils.isEmpty(links)) return;
        this.links.addAll(links);
        for (CampaignLink link : links) {
            link.setCampaign(this);
        }
    }

    public void addImage(List<CampaignImage> images) {
        if (CollectionUtils.isEmpty(images)) return;
        this.images.addAll(images);
        for (CampaignImage image : images) {
            image.setCampaign(this);
        }
    }

    public void addMission(List<Code> missions, Map<Long, String> argumentsMap) {
        if (CollectionUtils.isEmpty(missions)) return;
        for (int i = 0; i < missions.size(); i++) {
            this.missions.add(CampaignMission.of(argumentsMap.get(missions.get(i).getId()),this, missions.get(i)));
        }
    }

    public void addOption(List<Code> options) {
        if (CollectionUtils.isEmpty(options)) return;
        for (Code option : options) {
            this.options.add(CampaignOption.of(this, option));
        }
    }
}


