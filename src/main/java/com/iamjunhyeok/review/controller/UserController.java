package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.UserUpdateInfoRequest;
import com.iamjunhyeok.review.dto.UserUpdateInfoResponse;
import com.iamjunhyeok.review.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity<UserUpdateInfoResponse> updateMyInfo(@RequestBody @Valid UserUpdateInfoRequest request) {
        return ResponseEntity.ok(UserUpdateInfoResponse.from(userService.updateMyInfo(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateInfoResponse> updateUserInfo(@PathVariable Long id, @RequestBody @Valid UserUpdateInfoRequest request) {
        return ResponseEntity.ok(UserUpdateInfoResponse.from(userService.updateUserInfo(id, request)));
    }
}