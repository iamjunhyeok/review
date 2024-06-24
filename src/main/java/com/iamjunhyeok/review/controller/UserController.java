package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.UserJoinRequest;
import com.iamjunhyeok.review.dto.UserJoinResponse;
import com.iamjunhyeok.review.dto.UserUpdateInfoRequest;
import com.iamjunhyeok.review.dto.UserUpdatePasswordRequest;
import com.iamjunhyeok.review.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserJoinResponse> join(@RequestBody @Valid UserJoinRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserJoinResponse.from(userService.join(request)));
    }

    @PatchMapping("/me/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserUpdatePasswordRequest request) {
        userService.updatePassword(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateMyInfo(@RequestBody @Valid UserUpdateInfoRequest request) {
        userService.updateMyInfo(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUserInfo(@PathVariable Long id, @RequestBody @Valid UserUpdateInfoRequest request) {
        userService.updateUserInfo(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nickname/generate")
    public String generateNickname() {
        return userService.generateNickname();
    }
}