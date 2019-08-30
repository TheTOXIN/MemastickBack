package com.memastick.backmem.memotype.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum MemotypeRarity {

    CLASSIC(1),
    RARE(2),
    EPIC(3),
    LEGENDARY(4),
    INCREDIBLE(5);

    @Getter
    private int lvl;

    public static MemotypeRarity find(int lvl) {
        return Arrays
            .stream(MemotypeRarity.values())
            .filter(e -> e.getLvl() == lvl)
            .findFirst()
            .orElse(null);
    }
}
