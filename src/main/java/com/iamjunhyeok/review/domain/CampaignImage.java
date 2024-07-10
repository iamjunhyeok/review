package com.iamjunhyeok.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class CampaignImage extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    private boolean main = false;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    public static CampaignImage of(String name) {
        CampaignImage image = new CampaignImage();
        image.setName(name);
        return image;
    }

    public void makeMain() {
        this.main = true;
    }
}
