package com.memastick.backmem.shop.constant;

import lombok.Getter;

public enum PriceConst {
    COOKIE(5),
    TOKENS(100),
    RESSURECTION(150),
    NICK(500),
    PUBLISH(10000),
    MEMOTYPE(100);

    @Getter
    private int price;

    PriceConst(int price) {
        this.price =  price * -1;
    }
}
