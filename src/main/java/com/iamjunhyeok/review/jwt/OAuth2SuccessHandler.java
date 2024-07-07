package com.iamjunhyeok.review.jwt;

import com.iamjunhyeok.review.constant.Role;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.domain.RefreshToken;
import com.iamjunhyeok.review.repository.RefreshTokenRepository;
import com.iamjunhyeok.review.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProvider JWTProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        Long userId = customOAuth2User.getUserId();
        Role role = customOAuth2User.getRole();

        // 리프레시 토큰 생성
        String refreshToken = JWTProvider.generate(userId, role.name(), Duration.ofDays(10));
        // 리프레시 토큰 DB 저장
        refreshTokenRepository.findByUserId(userId)
                        .ifPresentOrElse(token -> refreshTokenRepository.save(token.update(refreshToken)),
                                () -> refreshTokenRepository.save(RefreshToken.of(refreshToken, userId)));

        // 리프레시 토큰 쿠키 생성
        CookieUtil.deleteCookie(response, "refresh_token");
        CookieUtil.createCookie(response, "refresh_token", refreshToken, (int) Duration.ofDays(1).toSeconds());

        // 액세스 토큰 생성
        String accessToken = JWTProvider.generate(userId, role.name(), Duration.ofMinutes(10));
        // 액세스 토큰 쿠키 생성
        CookieUtil.createCookie(response, "access_token", accessToken, (int) Duration.ofMinutes(10).toSeconds());

        // 리다이렉션
        response.sendRedirect("http://localhost:5173");
    }
}
