package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.CampaignCodeType;
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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CampaignCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignCodeType type;

    private String arguments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_id")
    private Code code;

    public static CampaignCode of(CampaignCodeType type, String arguments, Campaign campaign, Code code) {
        CampaignCode campaignCode = new CampaignCode();
        campaignCode.setType(type);
        campaignCode.setArguments(arguments);
        campaignCode.setCampaign(campaign);
        campaignCode.setCode(code);
        return campaignCode;
    }

    public static CampaignCode of(CampaignCodeType type, Campaign campaign, Code code) {
        CampaignCode campaignCode = new CampaignCode();
        campaignCode.setType(type);
        campaignCode.setCampaign(campaign);
        campaignCode.setCode(code);
        return campaignCode;
    }

    public void update(String newArguments) {
        this.arguments = newArguments;
    }
}
