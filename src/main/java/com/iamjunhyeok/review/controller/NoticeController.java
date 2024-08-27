package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.NoticeModifyRequest;
import com.iamjunhyeok.review.dto.request.NoticeRegisterRequest;
import com.iamjunhyeok.review.projection.NoticeProjection;
import com.iamjunhyeok.review.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/support/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody NoticeRegisterRequest request) {
        noticeService.register(request);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void modify(@PathVariable Long id, @RequestBody NoticeModifyRequest request) {
        noticeService.modify(id, request);
    }

    @GetMapping
    public List<NoticeProjection> fetchAll() {
        return noticeService.fetchAll();
    }

    @GetMapping("/{id}")
    public NoticeProjection fetchOne(@PathVariable Long id) {
        return noticeService.fetchById(id);
    }
}
