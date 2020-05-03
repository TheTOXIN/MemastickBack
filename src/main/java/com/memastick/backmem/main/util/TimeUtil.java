package com.memastick.backmem.main.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtil {

    public static Date toDate(LocalDateTime dateTime) {
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());

        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalTime minusTime(LocalDateTime from, LocalDateTime to) {
        return minusTime(
            from.toLocalTime(),
            to.toLocalTime()
        );
    }

    public static LocalTime minusTime(LocalTime from, LocalTime to) {
        return from
            .minusHours(to.getHour())
            .minusMinutes(to.getMinute())
            .minusSeconds(to.getSecond());
    }

    public static boolean isExpire(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }
}
