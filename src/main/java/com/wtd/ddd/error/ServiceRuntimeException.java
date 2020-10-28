package com.wtd.ddd.error;

/**
 * Created By mand2 on 2020-10-26.
 */
public abstract class ServiceRuntimeException extends RuntimeException {

    private final String message;
    private final String details;
    private final Object[] params;

    public ServiceRuntimeException(String message, String details, Object[] params) {
        this.message = message;
        this.details = details;
        this.params = params;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Object[] getParams() {
        return params;
    }
}
