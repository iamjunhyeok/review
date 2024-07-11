package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.DataDictionaryCreateRequest;
import com.iamjunhyeok.review.dto.DataDictionarySearchProjection;
import com.iamjunhyeok.review.dto.DataDictionaryUpdateRequest;
import com.iamjunhyeok.review.dto.DataDictionaryViewProjection;
import com.iamjunhyeok.review.service.DataDictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/data-dictionary")
public class DataDictionaryController {
    private final DataDictionaryService dataDictionaryService;

    @GetMapping
    public ResponseEntity<List<DataDictionarySearchProjection>> search(@RequestParam(name = "parent_id", required = false) Long parentId) {
        return ResponseEntity.ok(dataDictionaryService.search(parentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataDictionaryViewProjection> view(@PathVariable Long id) {
        return ResponseEntity.ok(dataDictionaryService.view(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody DataDictionaryCreateRequest request) {
        dataDictionaryService.create(request);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @RequestBody DataDictionaryUpdateRequest request) {
        dataDictionaryService.update(id, request);
    }
}
