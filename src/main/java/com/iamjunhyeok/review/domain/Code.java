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
public class Code extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String value;

    @Column(name = "orders", nullable = false)
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Code parent;

    public static Code of(String code, String value, Integer order, Code parent) {
        Code codeObj = new Code();
        codeObj.setCode(code);
        codeObj.setValue(value);
        codeObj.setOrder(order);
        codeObj.setParent(parent);
        return codeObj;
    }

    public void update(String value, Integer order) {
        this.value = value;
        this.order = order;
    }
}
