package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.VocaType;
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
public class Voca {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private VocaType type;

    @Column(nullable = false, unique = true)
    private String value;

    public static Voca of(VocaType type, String value) {
        Voca voca = new Voca();
        voca.setType(type);
        voca.setValue(value);
        return voca;
    }
}
