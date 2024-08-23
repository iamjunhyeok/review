package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.CodeCreateRequest;
import com.iamjunhyeok.review.dto.request.CodeUpdateRequest;
import com.iamjunhyeok.review.projection.CodeProjection;
import com.iamjunhyeok.review.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/codes")
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;

    @GetMapping
    public ResponseEntity<List<CodeProjection>> fetchAllByParentId(@RequestParam(value = "parent_id", required = false) Long parentId,
                                                                   @RequestParam(value = "parent_code", required = false) String parentCode) {
        if (parentId == null && Strings.isBlank(parentCode)) {
            return ResponseEntity.ok(codeService.findByParentIsNull());
        } else if (parentId != null) {
            return ResponseEntity.ok(codeService.fetchAllByParentId(parentId));
        } else {
            return ResponseEntity.ok(codeService.fetchAllByParentCode(parentCode));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody CodeCreateRequest request) {
        return codeService.create(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody CodeUpdateRequest request, @PathVariable Long id) {
        codeService.update(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        codeService.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.OK)
    public void restore(@PathVariable Long id) {
        codeService.restore(id);
    }
}
