package com.iamjunhyeok.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다."),
    CAMPAIGN_NOT_FOUND(HttpStatus.NOT_FOUND, "캠페인을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),
    PASSWORDS_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "신청서를 찾을 수 없습니다."),
    NOT_ONGOING_CAMPAIGN(HttpStatus.BAD_REQUEST, "진행 중인 캠페인이 아닙니다."),
    DUPLICATE_APPLICATION(HttpStatus.CONFLICT, "이미 신청된 캠페인입니다."),
    CAMPAIGN_CANNOT_BE_CANCELED(HttpStatus.CONFLICT, "취소할 수 없는 캠페인입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public ApplicationException build() {
        return new ApplicationException(httpStatus, message);
    }
}
