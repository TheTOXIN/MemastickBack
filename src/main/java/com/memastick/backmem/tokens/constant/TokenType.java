package com.memastick.backmem.tokens.constant;

import com.memastick.backmem.evolution.constant.EvolveStep;
import lombok.Getter;

public enum TokenType {

    TUBE(EvolveStep.ADAPTATION),
    SCOPE(null),
    MUTAGEN(null),
    CROSSOVER(null),
    ANTIBIOTIC(EvolveStep.SURVIVAL);

    @Getter
    private EvolveStep step;

    TokenType(EvolveStep step) {
        this.step = step;
    }
}
