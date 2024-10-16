package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.request.UserUpdateInfoRequest;
import com.iamjunhyeok.review.projection.UserProjection;
import com.iamjunhyeok.review.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserInfo(@PathVariable Long id,
                               @RequestBody @Valid UserUpdateInfoRequest request) throws IOException {
        userService.updateUserInfo(request, id);
    }

    /**
     * 전체 사용자 조회
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserProjection>> fetchAll(@RequestParam(required = false) Long id,
                                                       @RequestParam(required = false) String email,
                                                       @RequestParam(required = false) String nickname) {
        return ResponseEntity.ok(userService.fetchAll(id, email, nickname));
    }

    /**
     * 사용자 상세정보 조회
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserProjection> fetchOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.fetchOne(id));
    }
}