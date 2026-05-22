package com.peace.personalhealthmanagementplatform.common.response;

import com.peace.personalhealthmanagementplatform.common.constant.CommonConstants;
import com.peace.personalhealthmanagementplatform.common.error.ErrorCode;
import com.peace.personalhealthmanagementplatform.common.util.TraceIdUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApiResponse<T> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(CommonConstants.TIMESTAMP_PATTERN);

    private int code;
    private String message;
    private T data;
    private String traceId;
    private String timestamp;

    public ApiResponse() {
    }

    public ApiResponse(int code, String message, T data, String traceId, String timestamp) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.traceId = traceId;
        this.timestamp = timestamp;
    }

    public static ApiResponse<Void> success() {
        return of(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MESSAGE, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return of(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MESSAGE, data);
    }

    public static ApiResponse<Void> fail(ErrorCode errorCode) {
        return of(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static ApiResponse<Void> fail(ErrorCode errorCode, String message) {
        return of(errorCode.getCode(), message, null);
    }

    public static ApiResponse<Void> fail(int code, String message) {
        return of(code, message, null);
    }

    private static <T> ApiResponse<T> of(int code, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        response.setTraceId(TraceIdUtil.getOrCreateTraceId());
        response.setTimestamp(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        return response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
