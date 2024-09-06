package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.RegionGroupRegisterRequest;
import com.iamjunhyeok.review.projection.RegionGroupProjection;
import com.iamjunhyeok.review.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public List<RegionGroupProjection> getAllRegionGroups() {
        return regionService.getAllRegionGroups();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegionGroupRegisterRequest request) {
        regionService.registerRegionGroup(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public List<Long> getAllRegionGroupsByRegionId(@PathVariable Long id) {
        return regionService.getAll(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void modify(@PathVariable Long id, @RequestParam List<Long> codeIds) {
        regionService.modify(id, codeIds);
    }
}
