package com.memastick.backmem.tokens.constant;

import com.memastick.backmem.evolution.constant.EvolveStep;
import lombok.Getter;

public enum TokenType {

    TUBE(EvolveStep.ADAPTATION),
    SCOPE(EvolveStep.FITNESS),
    MUTAGEN(EvolveStep.MUTATION),
    CROSSOVER(EvolveStep.CROSSING),
    ANTIBIOTIC(EvolveStep.SURVIVAL);

    @Getter
    private EvolveStep step;

    TokenType(EvolveStep step) {
        this.step = step;
    }
}
