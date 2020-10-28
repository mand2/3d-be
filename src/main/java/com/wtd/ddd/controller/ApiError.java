package com.wtd.ddd.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

/**
 * Created By mand2 on 2020-10-24.
 */
public class ApiError {

    private final String message;
    private final int status;

    ApiError(Throwable throwable, HttpStatus httpStatus) {
        this.message = throwable.getMessage();
        this.status = httpStatus.value();
    }

    ApiError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.status = httpStatus.value();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("message", message)
                .append("status", status)
                .toString();
    }
}
