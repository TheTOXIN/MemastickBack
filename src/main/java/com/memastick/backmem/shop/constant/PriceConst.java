package com.memastick.backmem.shop.constant;

import lombok.Getter;

public enum PriceConst {
    COOKIE(3),
    TOKENS(30),
    RESSURECTION(100),
    NICK(500),
    PUBLISH(10000),
    MEMOTYPE(50);

    @Getter
    private int price;

    PriceConst(int price) {
        this.price =  price * -1;
    }
}
