package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.SnsType;
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
public class Sns extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String snsId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private SnsType type;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    private boolean deleted = false;

    public static Sns of(String snsId, SnsType type, User user) {
        Sns sns = new Sns();
        sns.snsId = snsId;
        sns.type = type;
        sns.user = user;
        return sns;
    }
}
