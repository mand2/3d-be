package com.wtd.ddd.error;

import org.apache.commons.lang3.StringUtils;

/**
 * Created By mand2 on 2020-10-26.
 */
public class NotFoundException extends ServiceRuntimeException {
    static final String MESSAGE = "NotFound";
    static final String MESSAGE_DETAILS = "Could not found ''{0}'' with query values ({1})";

    public NotFoundException(Class<?> cls, Object... values) {
        this(cls.getSimpleName(), values);
    }

    public NotFoundException(String targetName, Object... values) {
        super(MESSAGE, MESSAGE_DETAILS,
                new String[]{targetName, (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
