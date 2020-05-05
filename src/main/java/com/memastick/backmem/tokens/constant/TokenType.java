package com.memastick.backmem.tokens.constant;

import com.memastick.backmem.evolution.constant.EvolveStep;
import lombok.Getter;

public enum TokenType {

    TUBE(EvolveStep.ADAPTATION, 1),
    SCOPE(EvolveStep.FITNESS, 2),
    MUTAGEN(EvolveStep.MUTATION, 3),
    CROSSOVER(EvolveStep.CROSSING, 4),
    ANTIBIOTIC(EvolveStep.SURVIVAL, 5);

    @Getter
    private EvolveStep step;

    @Getter
    private int lvl;

    TokenType(EvolveStep step, int lvl) {
        this.step = step;
        this.lvl = lvl;
    }
}
