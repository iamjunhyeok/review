package com.iamjunhyeok.review.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String tokenType = TokenType.BEARER.getValue().concat(" ");

        if (authorizationHeader != null && authorizationHeader.startsWith(tokenType)) {
            String token = authorizationHeader.substring(tokenType.length());

            Long userId = jwtProvider.getUserId(token);
            if (userId != null) {
                String role = jwtProvider.getRole(token);

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                List<GrantedAuthority> authorities = new ArrayList<>(List.of(new SimpleGrantedAuthority(role)));

                Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login") || request.getServletPath().equals("/token/refresh"); // 로그인, 토큰 갱신은 필터링하지 않도록
    }

}
