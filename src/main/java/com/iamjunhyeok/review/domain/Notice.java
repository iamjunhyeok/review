package com.iamjunhyeok.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(nullable = false)
    private String categoryCode;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private boolean deleted = false;

    public static Notice of(String categoryCode, String title, String content) {
        Notice notice = new Notice();
        notice.setCategoryCode(categoryCode);
        notice.setTitle(title);
        notice.setContent(content);
        return notice;
    }

    public void modify(String categoryCode, String title, String content) {
        this.categoryCode = categoryCode;
        this.title = title;
        this.content = content;
    }
}
