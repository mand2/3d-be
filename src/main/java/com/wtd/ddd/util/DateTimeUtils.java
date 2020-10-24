package com.wtd.ddd.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created By mand2 on 2020-10-25.
 */
public class DateTimeUtils {

    public static Timestamp timestampOf(LocalDateTime time) {
        return time == null ? null : Timestamp.valueOf(time);
    }

    public static LocalDateTime dateTimeOf(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

}
