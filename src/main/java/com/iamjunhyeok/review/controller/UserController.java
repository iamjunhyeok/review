package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.dto.UserSearchProjection;
import com.iamjunhyeok.review.dto.UserUpdateInfoRequest;
import com.iamjunhyeok.review.dto.UserUpdateInfoResponse;
import com.iamjunhyeok.review.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity<UserUpdateInfoResponse> updateMyInfo(@RequestPart @Valid UserUpdateInfoRequest request,
                                                               @RequestPart MultipartFile file,
                                                               @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(UserUpdateInfoResponse.from(userService.updateUserInfo(user.getUserId(), request, file)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateInfoResponse> updateUserInfo(@PathVariable Long id, @RequestBody @Valid UserUpdateInfoRequest request) {
        return ResponseEntity.ok(UserUpdateInfoResponse.from(userService.updateUserInfo(id, request)));
    }

    @GetMapping
    public ResponseEntity<List<UserSearchProjection>> search() {
        return ResponseEntity.ok(userService.search());
    }
}