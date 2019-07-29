package com.memastick.backmem.main.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GlobalConstant {

    public static String URL;
    public static String VER;

    public static final LocalDate START_EVOLVE = LocalDate.of(2019, 1, 1);
    public static final int MAX_TOKEN = 10;
    public static final int CELL_GROWTH = 24;
    public static final int CELL_SIZE = 100;
    public static final int MAX_CHROMOSOME = 100;
    public static final int MAX_TEXT_LEN = 256;

    @Autowired
    public GlobalConstant(
        @Value("${memastick.front.url}") String url,
        @Value("${memastick.version}") String ver
    ) {
        GlobalConstant.URL = url;
        GlobalConstant.VER = ver;
    }
}
