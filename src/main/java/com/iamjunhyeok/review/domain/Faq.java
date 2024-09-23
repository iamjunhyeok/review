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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Faq extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code_id")
    private Code categoryCode;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    private boolean deleted = false;

    public static Faq of(Code categoryCode, String question, String answer) {
        Faq faq = new Faq();
        faq.setCategoryCode(categoryCode);
        faq.setQuestion(question);
        faq.setAnswer(answer);
        return faq;
    }

    public Faq update(Code categoryCode, String question, String answer) {
        this.categoryCode = categoryCode;
        this.question = question;
        this.answer = answer;
        return this;
    }

    public void delete() {
        this.deleted = true;
    }
}
