package com.memastick.backmem.shop.constant;

import lombok.Getter;

public enum  PriceConst {
    COOKIE(10),
    ALLOWANCE(1000),
    RESSURECTION(150),
    NICK(500),
    PUBLISH(10000);

    @Getter
    private int value;

    PriceConst(int value) {
        this.value =  value * -1;
    }
}
