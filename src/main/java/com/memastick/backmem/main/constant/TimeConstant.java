package com.memastick.backmem.main.constant;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeConstant {

    public static final ZonedDateTime START_TIME = ZonedDateTime.of(
        1970, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")
    );

    public static final ZonedDateTime END_TIME = ZonedDateTime.of(
        2070, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")
    );

    public static final LocalDateTime START_LOCAL_TIME = LocalDateTime.of(
        1970, 1, 1, 0, 0, 0, 0
    );

    public static final LocalDateTime END_LOCAL_TIME = LocalDateTime.of(
        2070, 1, 1, 0, 0, 0, 0
    );
}
