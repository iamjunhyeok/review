package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.projection.CodeProjection;
import com.iamjunhyeok.review.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/codes")
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;

    @GetMapping
    public ResponseEntity<List<CodeProjection>> fetchAllByParentId(@RequestParam("parent_id") Long parentId) {
        return ResponseEntity.ok(codeService.fetchAllByParentId(parentId));
    }
}
