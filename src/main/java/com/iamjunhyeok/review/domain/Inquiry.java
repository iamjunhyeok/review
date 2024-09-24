package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code_id")
    private Code categoryCode;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private InquiryAnswer inquiryAnswer;

    public static Inquiry of(Code categoryCode, String title, String content, User user) {
        Inquiry inquiry = new Inquiry();
        inquiry.setCategoryCode(categoryCode);
        inquiry.setTitle(title);
        inquiry.setContent(content);
        inquiry.setUser(user);
        return inquiry;
    }

    public Inquiry update(Code categoryCode, String title, String content) {
        if (this.inquiryAnswer != null) {
            throw ErrorCode.INQUIRY_CANNOT_BE_MODIFIED.build();
        }
        this.categoryCode = categoryCode;
        this.title = title;
        this.content = content;
        return this;
    }

    public void delete() {
        if (this.inquiryAnswer != null) {
            throw ErrorCode.INQUIRY_CANNOT_BE_DELETED.build();
        }
        this.deleted = true;
    }

    public void registerAnswer(InquiryAnswer inquiryAnswer) {
        if (this.inquiryAnswer != null) {
            throw ErrorCode.INQUIRY_ANSWER_ALREADY_REGISTERED.build();
        }
        this.inquiryAnswer = inquiryAnswer;
        inquiryAnswer.setInquiry(this);
    }
}
