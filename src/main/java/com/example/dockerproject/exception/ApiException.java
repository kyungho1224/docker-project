package com.example.dockerproject.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ApiErrorCode apiErrorCode;
    private final String errorMessage;

    public ApiException(ApiErrorCode apiErrorCode) {
        super(apiErrorCode.getErrorMessage());
        this.apiErrorCode = apiErrorCode;
        this.errorMessage = apiErrorCode.getErrorMessage();
    }

    public ApiException(ApiErrorCode apiErrorCode, String errorMessage) {
        super(errorMessage);
        this.apiErrorCode = apiErrorCode;
        this.errorMessage = errorMessage;
    }

}
