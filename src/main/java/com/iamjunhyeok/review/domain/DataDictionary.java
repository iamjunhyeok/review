package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.dto.DataDictionaryUpdateRequest;
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
public class DataDictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    @Column(name = "orders", nullable = false)
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private DataDictionary parent;

    public static DataDictionary of(String name, String value, Integer order, DataDictionary parent) {
        DataDictionary dictionary = new DataDictionary();
        dictionary.name = name;
        dictionary.value = value;
        dictionary.order = order;
        dictionary.parent = parent;
        return dictionary;
    }

    public void update(DataDictionaryUpdateRequest request) {
        this.name = request.getName();
        this.value = request.getValue();
        this.order = request.getOrder();
    }
}
