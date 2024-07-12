package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.ReviewStatus;
import com.iamjunhyeok.review.constant.ReviewType;
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
public class Review extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewType type;

    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status;

    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    public static Review of(ReviewType type, String url) {
        Review review = new Review();
        review.setType(type);
        review.setUrl(url);
        return review;
    }

    public Review modifyRequest(String details) {
        this.status = ReviewStatus.MODIFY_REQUEST;
        this.details = details;
        return this;
    }

    public void reconfirmRequest() {
        this.status = ReviewStatus.RECONFIRM_REQUEST;
    }

    public void confirm() {
        this.status = ReviewStatus.CONFIRMED;
    }
}
