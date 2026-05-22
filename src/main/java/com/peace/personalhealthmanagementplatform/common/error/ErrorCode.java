package com.peace.personalhealthmanagementplatform.common.error;

public enum ErrorCode {
    SUCCESS(0, "success"),
    PARAM_ERROR(40001, "invalid parameter"),
    UNAUTHORIZED(40101, "unauthorized"),
    FORBIDDEN(40301, "forbidden"),
    NOT_FOUND(40401, "resource not found"),
    CONFLICT(40901, "resource conflict"),
    BUSINESS_VALIDATION_FAILED(42201, "business validation failed"),
    SYSTEM_ERROR(50001, "system error");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorCode fromCode(int code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.code == code) {
                return errorCode;
            }
        }
        return SYSTEM_ERROR;
    }
}
