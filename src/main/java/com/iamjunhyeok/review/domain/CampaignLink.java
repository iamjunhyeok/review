package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.dto.CampaignLinkDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CampaignLink extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    public static CampaignLink of(String url) {
        CampaignLink campaignLink = new CampaignLink();
        campaignLink.setUrl(url);
        return campaignLink;
    }

    public static CampaignLink of(CampaignLinkDto dto) {
        CampaignLink campaignLink = new CampaignLink();
        campaignLink.setUrl(dto.getUrl());
        return campaignLink;
    }

    public void updateUrl(String url) {
        this.url = url;
    }
}
