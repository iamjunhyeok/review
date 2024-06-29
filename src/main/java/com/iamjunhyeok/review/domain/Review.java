package com.iamjunhyeok.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    private String receiptUrl;

    private String blogUrl;

    private String postUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    public static Review of(String receiptUrl, String blogUrl, String postUrl) {
        Review review = new Review();
        review.setReceiptUrl(receiptUrl);
        review.setBlogUrl(blogUrl);
        review.setPostUrl(postUrl);
        return review;
    }

    public Review update(String receiptUrl, String blogUrl, String postUrl) {
        this.receiptUrl = receiptUrl;
        this.blogUrl = blogUrl;
        this.postUrl = postUrl;
        return this;
    }
}
