package com.iamjunhyeok.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class InquiryAnswer extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    public static InquiryAnswer of(String title, String content) {
        InquiryAnswer inquiryAnswer = new InquiryAnswer();
        inquiryAnswer.setTitle(title);
        inquiryAnswer.setContent(content);
        return inquiryAnswer;
    }

    public InquiryAnswer update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }
}
