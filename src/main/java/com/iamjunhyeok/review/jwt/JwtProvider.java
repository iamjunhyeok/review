package com.iamjunhyeok.review.jwt;

import com.iamjunhyeok.review.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtProvider {

    private String issuer;
    private SecretKey secretKey;

    public JwtProvider(@Value("${jwt.issuer}") String issuer, @Value("${jwt.secret-key}") String secret) {
        this.issuer = issuer;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(User user, Duration expiredAt) {
        Date now = new Date();
        return Jwts.builder()
                .issuer(issuer)
                .issuedAt(now)
                .subject(String.valueOf(user.getId()))
                .expiration(new Date(now.getTime() + expiredAt.toMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generate(Long userId, String role, Duration expiredAt) {
        Date now = new Date();
        return Jwts.builder()
                .issuer(issuer)
                .setIssuedAt(now)
                .claim("id", userId)
                .claim("role", role)
                .setExpiration(new Date(now.getTime() + expiredAt.toMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generate(Long userId, String role, String profileImageName, Duration expiredAt) {
        Date now = new Date();
        return Jwts.builder()
                .issuer(issuer)
                .setIssuedAt(now)
                .claim("id", userId)
                .claim("role", role)
                .claim("profileImageName", profileImageName)
                .setExpiration(new Date(now.getTime() + expiredAt.toMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long validate(String token) {
        try {
            String subject = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getPayload()
                    .getSubject();
            return Long.valueOf(subject);
        } catch (JwtException e) {
            return null;
        }
    }

    public Long getUserId(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return payload.get("id", Long.class);
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }

    public String getRole(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return payload.get("role", String.class);
    }
}
