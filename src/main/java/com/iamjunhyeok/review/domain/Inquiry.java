package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.InquiryCategory;
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
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Inquiry extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryCategory category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private Answer answer;

    public static Inquiry of(InquiryCategory category, String title, String content, User user) {
        Inquiry inquiry = new Inquiry();
        inquiry.setCategory(category);
        inquiry.setTitle(title);
        inquiry.setContent(content);
        inquiry.setUser(user);
        return inquiry;
    }

    public Inquiry update(InquiryCategory category, String title, String content) {
        this.category = category;
        this.title = title;
        this.content = content;
        return this;
    }

    public void delete() {
        this.deleted = true;
    }

    public void registerAnswer(Answer answer) {
        this.answer = answer;
        answer.setInquiry(this);
    }
}
