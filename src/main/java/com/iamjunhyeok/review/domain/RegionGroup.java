package com.iamjunhyeok.review.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class RegionGroup extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String name;

    private String description;

    @Column(name = "orders")
    private int order;

    private boolean deleted = false;

    @OneToMany(mappedBy = "regionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegionMapping> regionMappings = new ArrayList<>();

    public static RegionGroup of(String name, String description, int order) {
        RegionGroup regionGroup = new RegionGroup();
        regionGroup.setName(name);
        regionGroup.setDescription(description);
        regionGroup.setOrder(order);
        return regionGroup;
    }

    public void addRegion(Code code) {
        regionMappings.add(RegionMapping.of(this, code));
    }
}
