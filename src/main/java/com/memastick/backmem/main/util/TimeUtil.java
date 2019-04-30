package com.memastick.backmem.main.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {

    public static Date toDate(LocalDateTime dateTime) {
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());

        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
