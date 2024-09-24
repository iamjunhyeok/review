package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.RefreshToken;
import com.iamjunhyeok.review.jwt.JwtProvider;
import com.iamjunhyeok.review.repository.RefreshTokenRepository;
import com.iamjunhyeok.review.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProvider jwtProvider;

    public String generateNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String tokenType = OAuth2AccessToken.TokenType.BEARER.getValue().concat(" ");

        if (authorizationHeader != null && authorizationHeader.startsWith(tokenType)) {
            String token = authorizationHeader.substring(tokenType.length());
            if (!jwtProvider.isValid(token)) {
                throw new IllegalArgumentException("Invalid token");
            }
            String newAccessToken = null;
            Long userId = jwtProvider.getUserId(token);
            if (userId != null) {
                // 토큰이 유효한지와 사용자의 리프레시 토큰이 맞는지 확인
                RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                        .orElseThrow(() -> new IllegalArgumentException("Token is invalid"));
                if (!refreshToken.getUserId().equals(userId)) {
                    throw new IllegalArgumentException("User is invalid");
                }
                newAccessToken = jwtProvider.generate(userId, jwtProvider.getRole(token), Duration.ofMinutes(30));

                // 리프레시 토큰 쿠키 저장
                CookieUtil.deleteCookie(response, "access_token");
                CookieUtil.createCookie(response, "access_token", newAccessToken, (int) Duration.ofMinutes(30).toSeconds());
            }
            return newAccessToken;
        }
        throw new IllegalArgumentException("Authorization header is invalid");
    }
}
