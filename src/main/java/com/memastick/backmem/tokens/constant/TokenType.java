package com.memastick.backmem.tokens.constant;

import com.memastick.backmem.evolution.constant.EvolveStep;
import lombok.Getter;

import java.util.Arrays;

public enum TokenType {

    TUBE(1),
    SCOPE(2),
    MUTAGEN(3),
    CROSSOVER(4),
    ANTIBIOTIC(5);

    @Getter
    private int lvl;

    TokenType(int lvl) {
        this.lvl = lvl;
    }
}
