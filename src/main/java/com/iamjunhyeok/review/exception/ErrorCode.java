package com.iamjunhyeok.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public ApplicationException build() {
        return new ApplicationException(httpStatus, message);
    }
}
