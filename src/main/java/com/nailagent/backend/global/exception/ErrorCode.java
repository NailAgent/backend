package com.nailagent.backend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "MISSING_PARAMETER", "요청 파라미터가 누락되었습니다."),
    INVALID_PARAMETER_TYPE(HttpStatus.BAD_REQUEST, "INVALID_PARAMETER_TYPE", "파라미터 타입이 올바르지 않습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "INVALID_INPUT_VALUE", "입력값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다."),

    // Reservation
    INVALID_PAGINATION(HttpStatus.BAD_REQUEST, "INVALID_PAGINATION", "페이지 값이 올바르지 않습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "RESERVATION_NOT_FOUND", "해당 예약을 찾을 수 없습니다."),

    // Customer
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "CUSTOMER_NOT_FOUND", "해당 고객을 찾을 수 없습니다."),

    // Payment
    DUPLICATE_PAYMENT_KEY(HttpStatus.CONFLICT, "DUPLICATE_PAYMENT_KEY", "이미 처리된 결제 키입니다."),
    PAYMENT_NOT_PAID(HttpStatus.BAD_REQUEST, "PAYMENT_NOT_PAID", "결제 완료 상태가 아니어서 환불할 수 없습니다."),

    // Shopinfo
    SHOPINFO_NOT_FOUND(HttpStatus.NOT_FOUND, "SHOPINFO_NOT_FOUND", "샵 정보를 찾을 수 없습니다."),

    // Google Calendar
    GOOGLE_CALENDAR_SYNC_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "GOOGLE_CALENDAR_SYNC_FAILED", "구글 캘린더 등록에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
