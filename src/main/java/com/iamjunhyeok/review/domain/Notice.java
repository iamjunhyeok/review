package com.iamjunhyeok.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Notice extends Base {
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

    public static Notice of(Code categoryCode, String title, String content) {
        Notice notice = new Notice();
        notice.setCategoryCode(categoryCode);
        notice.setTitle(title);
        notice.setContent(content);
        return notice;
    }

    public void modify(Code categoryCode, String title, String content) {
        this.categoryCode = categoryCode;
        this.title = title;
        this.content = content;
    }
}
