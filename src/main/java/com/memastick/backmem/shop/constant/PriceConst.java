package com.memastick.backmem.shop.constant;

import lombok.Getter;

public enum  PriceConst {
    COOKIE(10);

    @Getter
    private int value;

    PriceConst(int value) {
        this.value =  value * -1;
    }
}
