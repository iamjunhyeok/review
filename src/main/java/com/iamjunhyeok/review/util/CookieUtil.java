package com.iamjunhyeok.review.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public static void createCookie(HttpServletResponse response, String name, String value, int maxAge) {
//        ResponseCookie cookie = ResponseCookie.from(name, value)
//                .maxAge(maxAge)
//                .path("/")
//                .httpOnly(false)
//                .secure(true)
//                .sameSite("None")
//                .build();
//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
//        cookie.setHttpOnly(true);
//        "SameSite : none" 옵션을 사용하려면 "Secure" 옵션이 "True"로 설정되어야 함
//        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
