package com.example.dockerproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ApiErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SERVER ERROR"),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND.value(), "Not found member"),
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND.value(), "Not found store"),
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND.value(), "Not found product"),
    NOT_FOUND_NOTICE(HttpStatus.NOT_FOUND.value(), "Not found notice"),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND.value(), "Not found review"),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND.value(), "Not found comment"),
    ;

    private final Integer errorCode;
    private final String errorMessage;

}
