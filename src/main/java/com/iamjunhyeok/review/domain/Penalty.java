package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.PenaltyReason;
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
public class Penalty extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PenaltyReason reason;

    private String details;

    private int point;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    public static Penalty of(User user, Application application, PenaltyReason reason) {
        Penalty penalty = new Penalty();
        penalty.setUser(user);
        penalty.setApplication(application);
        penalty.setReason(reason);
        penalty.setPoint(reason.getPoint());
        return penalty;
    }

    public void delete() {
        this.deleted = true;
    }
}
