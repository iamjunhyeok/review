package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    /**
     * 리프레시토큰을 이용하여 새 엑세스토큰 발급
     * @param request
     * @param response
     */
    @PostMapping("/token/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        refreshTokenService.generateNewAccessToken(request, response);
    }
}
