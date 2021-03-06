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

    public static final int CELL_GROWTH = 24;
    public static final int CELL_SIZE = 100;
    public static final int MEMETICK_RATING = 10;
    public static final int BLOCK_DFCLT = 1;
    public static final int BLOCK_NONCE = 300;
    public static final int PICKAXE_HOURS = 1;
    public static final int AVATAR_CACHE = 24 * 60 * 60;
    public static final int DEFAULT_COOKIES = 10;
    public static final int DEFAULT_MEMCOINS = 100;
    public static final int EVOLVE_CHROMOSOME = 10;
    public static final int DONATE_RATE = 100;

    @Autowired
    public GlobalConstant(
        @Value("${memastick.front.url}") String url,
        @Value("${memastick.version}") String ver
    ) {
        GlobalConstant.URL = url;
        GlobalConstant.VER = ver;
    }
}
