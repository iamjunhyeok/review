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
public class Answer extends Base {
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

    public static Answer of(String title, String content) {
        Answer answer = new Answer();
        answer.setTitle(title);
        answer.setContent(content);
        return answer;
    }

    public Answer update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }
}
