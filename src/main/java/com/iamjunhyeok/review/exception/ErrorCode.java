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
    CAMPAIGN_CANNOT_BE_CANCELED(HttpStatus.BAD_REQUEST, "취소할 수 없는 캠페인입니다."),
    CAMPAIGN_CANNOT_BE_APPROVED(HttpStatus.BAD_REQUEST, "승인할 수 없는 캠페인입니다."),
    CAMPAIGN_CANNOT_BE_REJECTED(HttpStatus.BAD_REQUEST, "거부할 수 없는 캠페인입니다."),
    INQUIRY_NOT_FOUND(HttpStatus.NOT_FOUND, "문의를 찾을 수 없습니다."),
    INQUIRY_CANNOT_BE_MODIFIED(HttpStatus.BAD_REQUEST, "답변 완료된 문의는 수정할 수 없습니다."),
    INQUIRY_CANNOT_BE_DELETED(HttpStatus.BAD_REQUEST, "답변 완료된 문의는 삭제할 수 없습니다."),
    INQUIRY_ANSWER_ALREADY_REGISTERED(HttpStatus.CONFLICT, "문의에 대한 답변이 이미 등록되었습니다."),
    FAQ_NOT_FOUND(HttpStatus.NOT_FOUND, "FAQ 를 찾을 수 없습니다."),
    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "문의에 대한 답변을 찾을 수 없습니다."),
    PENALTY_NOT_FOUND(HttpStatus.NOT_FOUND, "패널티를 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    DATA_DICTIONARY_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "항목을 찾을 수 없습니다."),
    APPLICATION_CANNOT_BE_DELETED(HttpStatus.BAD_REQUEST, "내역을 삭제할 수 없습니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "로그인 정보가 만료되었습니다."),
    PENALTY_ALREADY_DELETED(HttpStatus.CONFLICT, "이미 삭제된 패널티입니다."),
    NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."),
    WITHDRAWAL_NOT_FOUND(HttpStatus.NOT_FOUND, "출금 신청건을 찾을 수 없습니다."),
    WITHDRAWAL_ALREADY_COMPLETED(HttpStatus.CONFLICT, "이미 출금 완료된 상태입니다."),
    CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "코드를 찾을 수 없습니다."),
    PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "요금제를 찾을 수 없습니다."),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "공지를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;

    public ApplicationException build() {
        return new ApplicationException(httpStatus, message);
    }
}
