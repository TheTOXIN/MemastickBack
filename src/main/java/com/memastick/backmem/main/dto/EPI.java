package com.memastick.backmem.main.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class EPI {

    @Getter
    private long evolution;

    @Getter
    private long population;

    @Getter
    private long individuation;
}
