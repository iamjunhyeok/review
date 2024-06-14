package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.UserChangePasswordRequest;
import com.iamjunhyeok.review.dto.UserJoinRequest;
import com.iamjunhyeok.review.dto.UserJoinResponse;
import com.iamjunhyeok.review.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserJoinResponse.from(userService.join(request)));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody UserChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }
}
