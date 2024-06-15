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
public class Inquiry extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Inquiry of(String title, String content, User user) {
        Inquiry inquiry = new Inquiry();
        inquiry.setTitle(title);
        inquiry.setContent(content);
        inquiry.setUser(user);
        return inquiry;
    }

    public void update(String title, String content) {
        this.setTitle(title);
        this.setContent(content);
    }

    public void delete() {
        this.setDeleted(true);
    }
}
